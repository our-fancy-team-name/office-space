package com.ourfancyteamname.officespace.db.entities.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`package_list_view`")
public class PackageListView implements Serializable {
  private static final long serialVersionUID = -256749914363931128L;

  @Id
  @Column(name = "`id`")
  private int id;

  @Column(name = "`product`")
  private String product;

  @Column(name = "`serial-number`")
  private String serialNumber;

  @Column(name = "`description`")
  private String description;
}
