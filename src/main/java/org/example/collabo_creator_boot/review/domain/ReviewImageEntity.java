package org.example.collabo_creator_boot.review.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "review_image")
public class ReviewImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_image_no")
    private Long reviewImageNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_no", referencedColumnName = "review_no")
    private ReviewEntity reviewNo;
}
