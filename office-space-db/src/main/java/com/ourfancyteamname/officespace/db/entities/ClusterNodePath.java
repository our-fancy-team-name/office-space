package com.ourfancyteamname.officespace.db.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`cluster_node_path`")
public class ClusterNodePath implements Serializable {
  private static final long serialVersionUID = -8909725113323730751L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "`id`")
  private int id;

  @Column(name = "`cluster-node-id-to`")
  private Integer clusterNodeIdTo;

  @Column(name = "`cluster-node-id-from`")
  private Integer clusterNodeIdFrom;

  @Column(name = "`label`")
  private String label;

  @Column(name = "`description`")
  private String description;
}
