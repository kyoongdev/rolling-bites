package kyoongdev.rolling_bites.common.dto;

public interface CreateDto<T> {

  T toEntity();

}
