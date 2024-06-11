package com.ne.template.models;

import com.ne.template.audits.TimestampAudit;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
@Entity

public class Category extends TimestampAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;
}