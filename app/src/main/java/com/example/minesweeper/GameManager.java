package com.example.minesweeper;

import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.LinkedList;
import java.util.Queue;

public class GameManager
{
    private static char[] choose={'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'M', 'M'};
    public static void buildGame(char[][] arr, int minMines)
    {
        int countMines= 0;
        for(int i=0;i<arr.length;i++)
            for(int j=0;j<arr[0].length;j++)
            {
                arr[i][j]= choose[(int)(Math.random()*10)];
                if(arr[i][j]== 'M')
                    countMines++;
            }

        if(countMines<minMines)
            buildGame(arr, minMines);
    }

    public static void onClick(char[][] board, int[] click)
    {
        if(board[click[0]][click[1]]=='M')
        {
            board[click[0]][click[1]]= 'X';
            return;
        }

        boolean[][] visited=new boolean[board.length][board[0].length];
        Queue<Integer> q=new LinkedList<Integer>();
        q.add(click[0]);
        q.add(click[1]);
        visited[click[0]][click[1]]= true;

        while(!q.isEmpty())
        {
            int size= q.size();

            for(int ii=0;ii<size/2;ii++)
            {
                int row= q.poll();
                int col= q.poll();

                int count=0;
                for(int i=Math.max(0, row-1);i<= Math.min(board.length-1, row+1);i++)
                {
                    for(int j=Math.max(0, col-1); j<= Math.min(board[0].length-1, col+1);j++)
                    {
                        if(board[i][j]== 'M'|| board[i][j]== 'X')
                            count++;
                    }
                }

                if(board[row][col]== 'X') continue;
                board[row][col]= count==0? 'B': ((char)(count+48));

                if(board[row][col]== 'B')
                {
                    for(int i=Math.max(0, row-1);i<= Math.min(board.length-1, row+1);i++)
                    {
                        for(int j=Math.max(0, col-1); j<= Math.min(board[0].length-1, col+1);j++)
                            if(!visited[i][j])
                            { q.add(i); q.add(j); visited[i][j]= true; }
                    }
                }
            }
        }

        for(int i=0;i<board.length;i++)
        {
            for(int j= 0;j<board[0].length;j++)
            {
                if(board[i][j]== 'M')
                {
                    int count= 0;
                    for(int row= Math.max(0, i-1);row<=Math.min(board.length-1, i+1);row++)
                        for(int col= Math.max(0, j-1);col<= Math.min(board[0].length-1, j+1); col++)
                            if(board[row][col]== 'E' || board[row][col]== 'M')
                                count++;

                    if(count==1)
                        board[i][j]= 'Z';
                }
            }
        }
    }
}
