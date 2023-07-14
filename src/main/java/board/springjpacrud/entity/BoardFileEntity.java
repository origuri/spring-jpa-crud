package board.springjpacrud.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "board_file_table")
public class BoardFileEntity extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    private String originalFileName;

    @Column(unique = true)
    private String storedFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    public static BoardFileEntity createFile(BoardEntity boardEntity, String originalFileName, String storedFileName){
        return BoardFileEntity.builder()
                .boardEntity(boardEntity)
                .originalFileName(originalFileName)
                .storedFileName(storedFileName)
                .build();
    }

}
