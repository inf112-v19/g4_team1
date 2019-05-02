package inf112.skeleton.app.boardelementtests;

import inf112.skeleton.app.base.actors.*;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.board.IBoardElement;
import inf112.skeleton.app.base.board.ITile;
import inf112.skeleton.app.base.board.Tile;
import inf112.skeleton.app.base.board.boardelement.Gear;
import inf112.skeleton.app.base.utils.*;



import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TileTest {

    @Test
    void containsTest(){

        Board board = new Board(10, 10);
        Pos pos = new Pos(0, 0);
        Player player1 = new Player("tobias");
        Robot robot = new Robot(pos, Direction.EAST, player1, board);
        player1.addRobot(robot);
        board.addTileObject(robot);

        assertEquals(board.get(pos).contains(robot),true);
    }
    @Test
    void getContentTest(){
        Board board = new Board(10, 10);
        Pos pos = new Pos(0, 0);
        Player player1 = new Player("tobias");
        Robot robot = new Robot(pos, Direction.EAST, player1, board);
        player1.addRobot(robot);

        Player player2 = new Player("tobias");
        Robot robot2 = new Robot(pos, Direction.EAST, player1, board);
        player2.addRobot(robot2);


        Gear gear= new Gear(Direction.EAST,pos,board);
        board.addTileObject(robot);
        board.addTileObject(gear);
        board.addTileObject(robot2);
        List<IBoardElement> list = new ArrayList<>();
        list.add(robot);
        list.add(gear);
        list.add(robot2);


        assertEquals(board.get(pos).getContent(),list);
    }
    @Test
    void removeContentTest(){
        Board board = new Board(10, 10);
        Pos pos = new Pos(0, 0);
        Player player1 = new Player("tobias");
        Robot robot = new Robot(pos, Direction.EAST, player1, board);
        player1.addRobot(robot);

        Player player2 = new Player("tobias");
        Robot robot2 = new Robot(pos, Direction.EAST, player1, board);
        player2.addRobot(robot2);


        Gear gear= new Gear(Direction.EAST,pos,board);
        board.addTileObject(robot);
        board.addTileObject(gear);
        board.addTileObject(robot2);
        List<IBoardElement> list = new ArrayList<>();
        list.add(robot);
        list.add(gear);
        list.add(robot2);
        assertEquals(board.get(pos).getContent(),list);
        list.remove(gear);
        board.get(pos).removeContent(gear);

        assertEquals(board.get(pos).getContent(),list);
    }
}
