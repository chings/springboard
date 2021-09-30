package springboard.mybatis.util;

import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.domain.Page;
import springboard.data.PageWrapper;

import java.util.List;
import java.util.function.Supplier;

import static springboard.data.PageWrapper.DEFAULT_PAGE_SIZE;

public class Queries {

    /*
     * pageNum is 0-based
     */
    public static <T> Page<T> paginate(Supplier<List<T>> query, int... pagination) {
        Integer pageNum = ArrayUtils.isNotEmpty(pagination) ? pagination[0]: null;
        if(pageNum != null) {
            Integer pageSize = pagination.length > 1 ? pagination[1] : DEFAULT_PAGE_SIZE;
            com.github.pagehelper.Page<T> result = PageHelper.startPage(pageNum + 1, pageSize)
                    .doSelectPage(() -> query.get());
            return new PageWrapper<>(result, pageNum, pageSize, result.getTotal());
        } else {
            List<T> result = query.get();
            return new PageWrapper<>(result, 0, result.size(), result.size());
        }
    }

}
