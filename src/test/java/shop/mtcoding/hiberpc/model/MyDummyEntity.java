package shop.mtcoding.hiberpc.model;

import shop.mtcoding.hiberpc.model.board.Board;
import shop.mtcoding.hiberpc.model.user.User;

public class MyDummyEntity {

    protected User newUser(String username){
        return User.builder()
                .username(username)
                .password("1234")
                .email(username+"@nate.com")
                .build();
    }


    protected Board newBoard(String title){
        return Board.builder()
                .title(title)
                .content(title)
                .author("홍길동")
                .build();
    }
}
