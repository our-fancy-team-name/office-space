package com.ourfancyteamname.officespace.db.entities.view;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`cluster_node_package_list_view`")
public class ProcessListView implements Serializable {
  private static final long serialVersionUID = -6415086020226482761L;

  public ProcessListView(String serial, Integer packageId) {
    this.serial = serial;
    this.packageId = packageId;
  }

  @Id
  @JsonIgnore
  @Column(name = "`id`")
  private UUID id;

  @Column(name = "`serial`")
  private String serial;

  @Column(name = "`package-id`")
  private Integer packageId;

  @Column(name = "`product-id`")
  private Integer productId;

  @Column(name = "`part-number`")
  private String partNumber;

  @Column(name = "`cluster-schematic`")
  private String clusterSchematic;

  @Column(name = "`cluster-current`")
  private String clusterCurrent;

  @Column(name = "`cluster-node-current`")
  private Integer clusterNodeCurrent;

  @Column(name = "`amount`")
  private Integer amount;

  @Column(name = "`status`")
  private String status;

  @Column(name = "`cluster-node-next`")
  private String clusterNodeNext;

  @Column(name = "`cluster-node-prev`")
  private String clusterNodePrev;

}
