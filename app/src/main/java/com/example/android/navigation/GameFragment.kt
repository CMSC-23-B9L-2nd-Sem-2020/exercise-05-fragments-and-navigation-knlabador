/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.navigation.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private lateinit var boardlight : Array<Array<Int>>
    private lateinit var board : List<List<View>>
    private var numOfClicks = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game, container, false)

        binding.game = this

        binding.submitButton.setOnClickListener {
            retry(board,boardlight)
        }

        setListeners()

        return binding.root
    }

    private fun setListeners() {
        board  = listOf(
                listOf(binding.box1,binding.box2,binding.box3,binding.box4,binding.box5),
                listOf(binding.box6,binding.box7,binding.box8,binding.box9,binding.box10),
                listOf(binding.box11,binding.box12,binding.box13,binding.box14,binding.box15),
                listOf(binding.box16,binding.box17,binding.box18,binding.box19,binding.box20),
                listOf(binding.box21,binding.box22,binding.box23,binding.box24,binding.box25)
        )

        boardlight = arrayOf(
                arrayOf(1,1,1,1,1),
                arrayOf(1,1,1,1,1),
                arrayOf(1,1,1,1,1),
                arrayOf(1,1,1,1,1),
                arrayOf(1,1,1,1,1)
        )

        for (i in 0..4) {
            for(j in 0..4) {
                board[i][j].setOnClickListener { flipLights(it,board,i,j,boardlight) }
            }
        }
    }

    private fun retry(board: List<List<View>>,light: Array<Array<Int>>) {
        for (i in 0..4) {
            for(j in 0..4) {
                board[i][j].setBackgroundColor(Color.WHITE)
                light[i][j] = 1
                numOfClicks = 0
                val text = "Clicks: $numOfClicks"
            }
        }
    }

    private fun change(view: View,i: Int,j: Int, light: Array<Array<Int>>) {
        if(light[i][j] == 1) {
            light[i][j] = 0
            view.setBackgroundColor(Color.BLACK)
        }
        else {
            light[i][j] = 1
            view.setBackgroundColor(Color.WHITE)
        }
    }

    private fun flipLights(view: View,board: List<List<View>>,i: Int,j: Int, light: Array<Array<Int>>) {
        var winFlag = 0
        numOfClicks++
        val text = "Clicks: $numOfClicks"
        change(view,i,j,light)

        if(i==0) { //row 1
            if(j==0) { //upper left box
                change(board[i][j+1],i,j+1,light)
            }
            else if(j==4) { //upper right box
                change(board[i][j-1],i,j-1,light)
            }
            else {
                change(board[i][j-1],i,j-1,light)
                change(board[i][j+1],i,j+1,light)
            }
            change(board[i+1][j],i+1,j,light)
        }

        else if(i==4) { //row 5
            if(j==0) { //upper left box
                change(board[i][j+1],i,j+1,light)
            }
            else if(j==4) { //upper right box
                change(board[i][j-1],i,j-1,light)
            }
            else {
                change(board[i][j-1],i,j-1,light)
                change(board[i][j+1],i,j+1,light)
            }
            change(board[i-1][j],i-1,j,light)

        }

        else if(j==0) { //col 1
            change(board[i-1][j],i-1,j,light)
            change(board[i+1][j],i+1,j,light)
            change(board[i][j+1],i,j+1,light)
        }

        else if(j==4) { //col 5
            change(board[i-1][j],i-1,j,light)
            change(board[i+1][j],i+1,j,light)
            change(board[i][j-1],i,j-1,light)
        }

        else {
            change(board[i-1][j],i-1,j,light)
            change(board[i+1][j],i+1,j,light)
            change(board[i][j-1],i,j-1,light)
            change(board[i][j+1],i,j+1,light)
        }

        //checks if the user has already flip all the lights
        for (a in 0..4) {
            for(b in 0..4) {
                if(light[a][b] == 0) {
                    winFlag++
                }
                else {
                    continue
                }
            }
        }

        if(winFlag == 25) {
            view.findNavController().navigate(R.id.action_gameFragment_to_gameWonFragment)
        }
    }
}

