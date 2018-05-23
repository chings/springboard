package springboard.mybatis;

import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InOperationLanguageDriver extends XMLLanguageDriver implements LanguageDriver {

    private static final Pattern IN_PATTERN = Pattern.compile("\\s+(in)\\s+#\\{([^\\}]+)\\}", Pattern.CASE_INSENSITIVE);

    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
        Matcher matcher = IN_PATTERN.matcher(script);
        if (matcher.find()) {
            script = matcher.replaceAll(" $1 (<foreach collection='$2' item='__item' separator=','>#{__item}</foreach>)");
        }
        script = "<script>" + script + "</script>";
        return super.createSqlSource(configuration, script, parameterType);
    }

}
