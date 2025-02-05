package kyoongdev.rolling_bites.modules.user.enums;

import jakarta.persistence.AttributeConverter;

public class SocialTypeConverter implements AttributeConverter<SocialType, Integer> {


  @Override
  public Integer convertToDatabaseColumn(SocialType socialType) {
    if (socialType == null) {
      return null;
    }
    return socialType.getSocialType();
  }

  @Override
  public SocialType convertToEntityAttribute(Integer dbData) {
    if (dbData == null) {
      return null;
    }
    for (SocialType type : SocialType.values()) {
      if (type.getSocialType().equals(dbData)) {
        return type;
      }
    }

    throw new IllegalArgumentException("Unknown social type: " + dbData);


  }


}
