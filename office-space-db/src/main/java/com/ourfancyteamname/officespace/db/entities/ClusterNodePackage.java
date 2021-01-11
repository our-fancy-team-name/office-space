package com.ourfancyteamname.officespace.db.entities;

import com.ourfancyteamname.officespace.enums.PackageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`cluster_node_package`")
public class ClusterNodePackage implements Serializable {
  private static final long serialVersionUID = 4834156166615994163L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "`id`")
  private int id;

  @Column(name = "`cluster-node-id`")
  private Integer clusterNodeId;

  @Column(name = "`package-id`")
  private Integer packageId;

  @Basic(optional = false)
  @Column(name = "`add-time`", insertable = false, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date addTime;

  @Column(name = "`amount`")
  private Integer amount;

  @Column(name = "`status`")
  private PackageStatus status;
}
