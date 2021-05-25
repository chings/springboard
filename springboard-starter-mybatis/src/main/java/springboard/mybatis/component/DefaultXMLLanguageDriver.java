package springboard.mybatis.component;

import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultXMLLanguageDriver extends MybatisXMLLanguageDriver {

    private static final Pattern collectionPattern = Pattern.compile("\\(#\\{(\\w+)\\}\\)");

    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
        Matcher matcher = collectionPattern.matcher(script);
        if (matcher.find()) {
            script = matcher.replaceAll("<foreach collection=\"$1\" item=\"_item\" open=\"(\" separator=\",\" close=\")\" >#{_item}</foreach>");
        }

        if(!script.startsWith("<script>")) script = "<script>" + script;
        if(!script.endsWith("</script>")) script = script + "</script>";

        return super.createSqlSource(configuration, script, parameterType);
    }

}
