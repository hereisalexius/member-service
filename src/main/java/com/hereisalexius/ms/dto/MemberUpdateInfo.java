package com.hereisalexius.ms.dto;

import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.HashMap;
import java.util.Map;

@XmlRootElement
@XmlType(propOrder = {"firstName", "lastName", "birthDate", "postalCode", "imageUrl"})
public class MemberUpdateInfo {


    private String firstName;
    private String lastName;
    @Pattern.List({@Pattern(regexp = "(([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])))", message = "Unexceptable date format![yyyy-mm-dd]")})
    private String birthDate;
    @Pattern.List({@Pattern(regexp = "([0-9]{5})", message = "Unexceptable postal code format![5 digits]")})
    private String postalCode;
    @Pattern.List({@Pattern(regexp = "(http(s?):/)(/[^/]+)+\\.(?:jpg|gif|png)", message = "Image url is invalid!")})
    private String imageUrl;

    public MemberUpdateInfo() {
    }

    public MemberUpdateInfo(String firstName, String lastName, String birthDate, String postalCode, String imageUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.postalCode = postalCode;
        this.imageUrl = imageUrl;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public Map<String, Object> asMap() {
        //Here iv become lazy & my pc is too old for this
        Map<String, Object> map = new HashMap<>();

        if (firstName != null && !firstName.isEmpty()) {
            map.put("firstName", firstName);
        }

        if (lastName != null && !lastName.isEmpty()) {
            map.put("lastName", lastName);
        }

        if (birthDate != null && !birthDate.isEmpty()) {
            map.put("birthDate", birthDate);
        }


        if (postalCode != null && !postalCode.isEmpty()) {
            map.put("postalCode", postalCode);
        }

        if (imageUrl != null && !imageUrl.isEmpty()) {
            map.put("imageUrl", imageUrl);
        }
        return map;
    }
}
