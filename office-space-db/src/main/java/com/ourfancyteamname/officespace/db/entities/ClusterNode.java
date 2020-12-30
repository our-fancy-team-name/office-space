package com.ourfancyteamname.officespace.db.entities;

import com.ourfancyteamname.officespace.enums.ClusterNodePosition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`cluster_node`")
public class ClusterNode implements Serializable {

  private static final long serialVersionUID = -5932551914496136402L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "`id`")
  private int id;

  @Column(name = "`cluster-id`")
  private Integer clusterId;

  @Column(name = "`node-id`")
  private Integer nodeId;

  @Column(name = "`position`")
  private ClusterNodePosition position;

  @Column(name = "`description`")
  private String description;
}
