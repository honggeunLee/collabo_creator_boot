package org.example.collabo_creator_boot.qna.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "qna_image")
public class QnAImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qna_image_no")
    private Long qnaImageNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_no", referencedColumnName = "qna_no")
    private QnAEntity qnaNo;
}
