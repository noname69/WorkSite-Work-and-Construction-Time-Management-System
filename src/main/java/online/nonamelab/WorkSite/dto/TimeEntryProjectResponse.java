package online.nonamelab.WorkSite.dto;

public record TimeEntryProjectResponse(

        Long id,

        double hours,

        double distance,

        Long constructionSiteId,

        String constructionSiteName
) {
}
