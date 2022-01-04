package nce.majorproject.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * @author slap4msth
 * created-date: 2021-02-19
 * package-name: nce.majorproject.dto
 * project-name: majorproject
 */
@Getter
@Builder
public class CountStatResponse {
    private int maleUserCount;
    private int femaleUserCount;
    private int totalUsers;
    private Double checkedOutCount;
    private Double checkOutTotalAmount;
}
