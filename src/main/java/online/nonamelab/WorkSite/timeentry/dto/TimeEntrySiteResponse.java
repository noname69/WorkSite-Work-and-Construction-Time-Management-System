package online.nonamelab.WorkSite.timeentry.dto;

public record TimeEntrySiteResponse(

        Long id,

        double hours,

        double distance,

        Long constructionSiteId,

        String constructionSiteName
) {
}
