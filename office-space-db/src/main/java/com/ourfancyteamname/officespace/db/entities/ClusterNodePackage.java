package com.ourfancyteamname.officespace.db.entities;

import com.ourfancyteamname.officespace.enums.PackageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.ZonedDateTime;

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

  @CreationTimestamp
  @Column(name = "`add-time`")
  private ZonedDateTime addTime;

  @Column(name = "`amount`")
  private Integer amount;

  @Column(name = "`status`")
  private PackageStatus status;
}
