package shop.mtcoding.hiberpc.model.board;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import shop.mtcoding.hiberpc.model.MyDummyEntity;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import({BoardRepository.class})
@DataJpaTest
public class BoardRepositoryTest extends MyDummyEntity {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void setUp(){
        em.createNativeQuery("ALTER TABLE board_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
    }

    @Test
    public void save_test(){
        // given
        Board board = newBoard("제목1");

        // when
        Board boardPS = boardRepository.save(board);
        System.out.println("테스트 : "+boardPS);

        // then
        assertThat(boardPS.getId()).isEqualTo(1);
    }

    @Test
    public void findById_test(){
        // given 1
        boardRepository.save(newBoard("제목1"));

        // given 2
        int id = 1;

        // when
        Board boardPS = boardRepository.findById(id);

        // then
        assertThat(boardPS.getTitle()).isEqualTo("제목1");
    }

    @Test
    public void update_test(){
        // given 1
        boardRepository.save(newBoard("제목1"));
        em.clear();

        // given 2
        String title = "제목12";
        String content = "내용12";

        // when
        Board boardPS = boardRepository.findById(1);
        boardPS.update(title, content);
        em.flush(); // 트랜잭션 종료시 자동 발동됨

        // then
        Board findBoardPS = boardRepository.findById(1);
        assertThat(findBoardPS.getContent()).isEqualTo("내용12");
    }

    @Test
    public void delete_test(){
        // given 1 - DB에 영속화
        Board board = newBoard("제목1");
        boardRepository.save(board);

        // given 2 - request 데이터 (Lazy, Eager 쿼리 테스트)
        // em.clear();
        int id = 1;
        Board findBoardPS = boardRepository.findById(id);

        // when
        boardRepository.delete(findBoardPS);

        // then
        Board deleteBoardPS = boardRepository.findById(1);
        Assertions.assertThat(deleteBoardPS).isNull();
    }

    @Test
    public void findAll_test(){
        // given
        List<Board> boardList = Arrays.asList(newBoard("제목1"), newBoard("제목2"));
        boardList.stream().forEach((board)->{
            boardRepository.save(board);
        });

        // when
        List<Board> boardListPS = boardRepository.findAll();
        //System.out.println("테스트 : "+userListPS);

        // then
        assertThat(boardListPS.size()).isEqualTo(2);
    }
}
