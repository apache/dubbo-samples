package com.alibaba.dubbo.samples.validation.api;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * ValidationService
 * <p>
 * Use service interface to distinguish validation scenario, for example: @NotNull(groups = ValidationService.class)
 */
public interface ValidationService {

    void save(ValidationParameter parameter);

    void update(ValidationParameter parameter);

    void delete(@Min(1) long id, @NotNull @Size(min = 2, max = 16) @Pattern(regexp = "^[a-zA-Z]+$") String operator);

    /**
     * annotation which has the same name with the method but has the first letter in capital
     * used for distinguish validation scenario, for example: @NotNull(groups = ValidationService.Save.class)
     * optional
     */
    @interface Save {
    }

    /**
     * annotation which has the same name with the method but has the first letter in capital
     * used for distinguish validation scenario, for example: @NotNull(groups = ValidationService.Update.class)
     * optional
     */
    @interface Update {
    }
}
