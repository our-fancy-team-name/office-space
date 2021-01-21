package com.ourfancyteamname.officespace.db.converters.enums;

import com.ourfancyteamname.officespace.enums.PersistableEnum;

import javax.persistence.AttributeConverter;
import java.util.function.Supplier;

public abstract class AbstractHibernateEnumConverter<E extends Enum<E> & PersistableEnum<S>, S>
    implements AttributeConverter<E, S> {

  protected abstract Class<E> getClazz();

  @Override
  public S convertToDatabaseColumn(E attribute) {
    return attribute == null ? null : attribute.getName();
  }

  @Override
  public E convertToEntityAttribute(S dbData) {
    if (dbData == null) return null;
    E[] enums = getClazz().getEnumConstants();
    for (E e : enums) {
      if (e.getName().equals(dbData)) {
        return e;
      }
    }
    throw error(dbData).get();
  }

  private Supplier<IllegalArgumentException> error(S dbData) {
    return () -> new IllegalArgumentException(String.format("%s cannot convert to enum %s", dbData, getClazz()));
  }
}
