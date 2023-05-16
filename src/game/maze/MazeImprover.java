package game.maze;

import game.GameModel;
import java.lang.Math;

public class MazeImprover {
    public static void improveMaze(GameModel gameModel)
    {
        for( int row = 1; row < gameModel.getHeight() - 2; row++)
        {
//            int col = gameModel.getWidth() / 2;
//            int direction = 1;

            int col = (int) ( ( gameModel.getWidth() - 2 ) * Math.random() ) + 1;
            int direction = Math.random() > 0.5 ? 1 : -1;

            while( true )
            {
                if( col < 1 || col >= gameModel.getWidth() - 1 )
                    break;

                if(gameModel.elementIsWall(col, row) )
                {
                    if
                    (
                            gameModel.elementIsPoint( col, row - 1 )
                                    && gameModel.elementIsPoint( col, row + 1)
                                    && ( gameModel.elementIsWall( col - 1, row )
                                    && gameModel.elementIsWall( col + 1, row ) )
                    )
                    {
                        gameModel.setElementToPoint( col, row );
                        break;
                    }
                }

                col += direction;
            }
        }

        for( int col = 1; col < gameModel.getWidth() - 2; col++)
        {
            int row = (int) ( ( gameModel.getHeight() - 2 ) * Math.random() ) + 1;
            int direction = Math.random() > 0.5 ? 1 : -1;

            while( true )
            {
                if( row < 1 || row >= gameModel.getHeight() - 1 )
                    break;

                if(gameModel.elementIsWall(col, row) )
                {
                    if
                    (
                            gameModel.elementIsPoint( col - 1, row )
                         && gameModel.elementIsPoint( col + 1, row )
                        && ( gameModel.elementIsWall( col, row - 1 ) && gameModel.elementIsWall( col, row + 1 ) )
                    )
                    {
                        gameModel.setElementToPoint( col, row );
                        break;
                    }
                }

                row += direction;
            }
        }
    }
}
