package kyoongdev.rolling_bites.common.dto;

public abstract class FromEntityDto {

  protected abstract <T> T buildDtoFromEntity();

  public static <T> T fromEntity() {
    return null;
  }
  
}
