package kyoongdev.rolling_bites.common.dto;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public abstract class UpdateDto<Entity> {

  public abstract Entity toEntity(Entity origin);

  //Null 값 인 property 를 찾아서 String[] 로 반환
  public String[] getNullPropertyNames(Object source) {
    final BeanWrapper src = new BeanWrapperImpl(source);
    PropertyDescriptor[] pds = src.getPropertyDescriptors();

    Set<String> emptyNames = new HashSet<String>();
    for (PropertyDescriptor pd : pds) {
      Object srcValue = src.getPropertyValue(pd.getName());
      if (srcValue == null) {
        emptyNames.add(pd.getName());
      }
    }

    String[] result = new String[emptyNames.size()];
    return emptyNames.toArray(result);
  }

  //Null 값 인 property 를 제외하고 복사
  public void myCopyProperties(Object target) {
    BeanUtils.copyProperties(this, target, getNullPropertyNames(this));
  }


}
