package com.example.springfishproject.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class AddResultFishingForm {

    @NotEmpty(message = "{fish.Name.NotEmpty}")
//    @Length(min = 1, max = 255 ,message="{fish.Name.length}")
    private String addFishName;

    @Positive(message = "{fish.size.Positive}")
    private String addFishSize;

//    @NotEmpty
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String date;

    @Pattern(regexp = "[0-9]{4}")
    private String dateYear;

    @Pattern(regexp = "[0-9]{2}")
    private String dateMonth;

    @Pattern(regexp = "[0-9]{2}")
    private String dateDay;

    private String weather;

    @Length(min=0, max=2000)
    private String addFishDescription;

    private String tideId;

    private String placeId;

    private String imgPath;

    private int id;

}
