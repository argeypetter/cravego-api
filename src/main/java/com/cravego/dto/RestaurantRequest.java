package com.cravego.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantRequest {

    @NotBlank(message = "Restaurant name is requiered")
    @Size(max = 100, message = "The name cannot exceed 100 characters.")
    private String name;

    @Size(max = 255, message = "The description cannot exceed 255 characters.")
    private String description;

    @Size(max = 255, message = "The address cannot exceed 255 characters.")
    private String address;

    @Size(max = 20, message = "The phone cannot exceed 20 characters.")
    private String phone;

    @Size(max = 255, message = "The image cannot exceed 255 characters.")
    private String image;

    private boolean active;


}
