package nce.majorproject.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PaginationUtil {
    public static Pageable performPagination(int page, int size){
        Pageable pageable= PageRequest.of((page-1),size);
        return pageable;
    }
}
