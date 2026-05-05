package online.nonamelab.WorkSite.user.dto;

import online.nonamelab.WorkSite.model.Role;
import lombok.Data;

@Data
public class UserFilter {
    private String search;
    private Role role;
    private Boolean deleted;
}
