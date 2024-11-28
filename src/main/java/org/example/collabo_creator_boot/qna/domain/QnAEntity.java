package org.example.collabo_creator_boot.qna.domain;

import jakarta.persistence.*;
import org.example.collabo_creator_boot.common.BasicEntity;
import org.example.collabo_creator_boot.customer.domain.CustomerEntity;
import org.example.collabo_creator_boot.product.domain.ProductEntity;

@Entity
@Table(name = "qna")
public class QnAEntity extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="qna_no", nullable = false)
    private Long qnaNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no", referencedColumnName = "product_no")
    private ProductEntity productNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private CustomerEntity customerId;

    @Column(name = "question", nullable = false)
    private String question;

    @Column(name = "answer", nullable = false)
    private String answer;
}
