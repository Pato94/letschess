package com.pato.letsplaychess

import android.app.Activity
import android.content.ClipData
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_game.*
import android.widget.TextView

class GameActivity : Activity() {

    inner class SlotDragListener(val column: Int, val row: Int): View.OnDragListener {
        override fun onDrag(v: View?, event: DragEvent?): Boolean {
            if (v == null || event == null) return false
            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> { }
                DragEvent.ACTION_DRAG_ENTERED -> { }
                DragEvent.ACTION_DRAG_EXITED -> { }
                DragEvent.ACTION_DROP -> {
                    @Suppress("UNCHECKED_CAST")
                    val pair = event.localState as Pair<View, Slot>
                    val view = pair.first
                    val slot = pair.second
                    if (boardModel.pieceFromTo(slot.column, slot.row, column, row, slot.piece!!)) {
                        val owner = view.parent as FrameLayout
                        owner.removeView(view)
                        slot.column = column
                        slot.row = row
                        val container = v as FrameLayout
                        container.addView(view)
                    }
                    view.visibility = View.VISIBLE
                }
                DragEvent.ACTION_DRAG_ENDED -> { }
                else -> { }
            }
            return true
        }
    }

    val boardModel: Board = Board()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        createBoard()
        addPieces()
    }

    private fun addPieces() {
        boardModel.slots.filter { it.piece != null }
                .forEach { addPiece(it) }
    }

    private fun inflatePiece(slot: Slot): View {
        val view = TextView(this)
        view.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        view.text = when (slot.piece!!) {
            is Pawn -> "P"
            is Rook -> "R"
            is Knight -> "N"
            is Bishop -> "B"
            is Queen -> "Q"
            is King -> "K"
            else -> throw IllegalStateException("Unknown piece")
        }

        view.text = view.text.toString() + slot.piece!!.team.name

        view.setTextColor(getColor(R.color.colorAccent))
        view.gravity = Gravity.CENTER

        return view
    }

    private fun addPiece(slot: Slot) {
        val pieceView: View = inflatePiece(slot)
        ((board.getChildAt(8 - slot.row) as LinearLayout).getChildAt(slot.column - 1) as FrameLayout).addView(pieceView)

        val touchListener = View.OnTouchListener { view, motionEvent ->
            if (motionEvent?.action == MotionEvent.ACTION_DOWN) {
                val data = ClipData.newPlainText("", "")
                val shadowBuilder = View.DragShadowBuilder(
                        view)
                view?.startDragAndDrop(data, shadowBuilder, Pair(view, slot), 0)
                view?.visibility = View.INVISIBLE
                true
            } else {
                false
            }
        }

        pieceView.setOnTouchListener(touchListener)
    }

    private fun createBoard() {
        val boardRows = (1..8).map { LinearLayout(this) }

        boardRows.forEach { row ->
            row.orientation = LinearLayout.HORIZONTAL
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            row.layoutParams = params
        }

        boardRows.forEachIndexed { rowIndex, row ->
            val rowSlots = (1..8).map {
                FrameLayout(this)
            }

            rowSlots.forEachIndexed { index, slot ->
                val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
                params.weight = 1f
                slot.layoutParams = params
                if ((rowIndex + index) % 2 == 1) {
                    slot.setBackgroundColor(Color.rgb(0, 0, 0))
                } else {
                    slot.setBackgroundColor(Color.rgb(255, 255, 255))
                }
            }

            rowSlots.forEach { row.addView(it) }
        }

        boardRows.forEach { board.addView(it) }

        board.post {
            boardRows.forEachIndexed { rowIndex, row ->
                (1..row.childCount).forEach {
                    val slot = row.getChildAt(it - 1)
                    val params = LinearLayout.LayoutParams(0, slot.width)
                    params.weight = 1f
                    slot.layoutParams = params
                    slot.setOnDragListener(SlotDragListener(it, 8 - rowIndex))
                }
            }

            board.visibility = View.VISIBLE
        }
    }
}
