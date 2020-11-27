package com.ourfancyteamname.officespace.db.converters;

import com.ourfancyteamname.officespace.enums.PersistableEnum;
import lombok.AllArgsConstructor;

import javax.persistence.AttributeConverter;
import java.util.function.Supplier;

@AllArgsConstructor
public abstract class AbstractHibernateEnumConverter<E extends Enum<E> & PersistableEnum<S>, S>
    implements AttributeConverter<E, S> {

  private final Class<E> clazz;

  private Supplier<IllegalArgumentException> error(S dbData) {
    return () -> new IllegalArgumentException(String.format("%s cannot convert to enum %s", dbData, clazz));
  }

  @Override
  public S convertToDatabaseColumn(E attribute) {
    return attribute == null ? null : attribute.getName();
  }

  @Override
  public E convertToEntityAttribute(S dbData) {
    E[] enums = clazz.getEnumConstants();
    for (E e : enums) {
      if (e.getName().equals(dbData)) {
        return e;
      }
    }
    throw error(dbData).get();
  }
}
