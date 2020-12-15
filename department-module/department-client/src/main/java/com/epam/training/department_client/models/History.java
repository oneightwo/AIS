package com.epam.training.department_client.models;

import com.epam.training.common_department_api.dto.DepartmentWithoutChildrenDto;
import com.epam.training.department_client.constants.HistoryType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "histories")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "history_seq")
    @SequenceGenerator(name = "history_seq", sequenceName = "history_seq", allocationSize = 1)
    @Column(name = "history_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private HistoryType type;

    @Type(type = "jsonb")
    @Column(name = "old_department", columnDefinition = "jsonb")
    private DepartmentWithoutChildrenDto oldDepartment;

    @Type(type = "jsonb")
    @Column(name = "new_department", columnDefinition = "jsonb")
    private DepartmentWithoutChildrenDto newDepartment;

    @CreationTimestamp
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
}
