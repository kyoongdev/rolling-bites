package kyoongdev.rolling_bites.common.exception;


import lombok.Builder;

@Builder
public record ErrorDto(Integer status, String timestamp, String path, String message) {

}
