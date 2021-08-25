package springboard.mybatis.component;

import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * supported grammar:
 *  field in #{values}
 *  field IN (#{values})
 *  field In ( #{ values } )
 */

public class DefaultXMLLanguageDriver extends MybatisXMLLanguageDriver {

    private static final Pattern collectionPattern = Pattern.compile("([Ii][Nn]\\s+)(\\(?)(\\s*#\\{\\s*)(\\w+)(\\s*\\}\\s*)(\\)?)");

    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
        Matcher matcher = collectionPattern.matcher(script);
        if (matcher.find()) {
            StringBuilder replacement = new StringBuilder("$1<foreach collection=\"$4\" item=\"$4_item\" open=\"(\" separator=\",\" close=\")\" >#{$4_item}</foreach>");
            if(matcher.group(2).isEmpty()) replacement.append(matcher.group(6));
            script = matcher.replaceAll(replacement.toString());
        }

        if(!script.startsWith("<script>")) script = "<script>" + script;
        if(!script.endsWith("</script>")) script = script + "</script>";

        return super.createSqlSource(configuration, script, parameterType);
    }

}
