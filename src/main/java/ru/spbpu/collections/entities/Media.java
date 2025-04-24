package ru.spbpu.collections.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "medias")
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "size")
    private Double size;

    @Column(name = "hash", length = Integer.MAX_VALUE)
    private String hash;

    @Column(name = "caption", length = Integer.MAX_VALUE)
    private String caption;

    @Column(name = "datetime")
    private Timestamp datetime;

    @Column(name = "file")
    private byte[] file;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "mediatype", nullable = false)
    private Mediatype mediatype;

}