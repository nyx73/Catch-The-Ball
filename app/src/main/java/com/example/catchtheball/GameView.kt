package com.example.catchtheball

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View
import kotlin.random.Random

class GameView(context: Context) : View(context) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var ballX = 200f
    private var ballY = 200f
    private val ballRadius = 40f
    private var ballSpeedX = 8f
    private var ballSpeedY = 12f

    private var paddleX = 300f
    private var paddleY = 1200f
    private val paddleWidth = 250f
    private val paddleHeight = 40f

    private var score = 0
    private var level = 1
    private var gameOver = false
    private var gameWin = false

    private var ballShader = RadialGradient(
        ballX, ballY, ballRadius * 1.5f,
        intArrayOf(Color.RED, Color.YELLOW, Color.TRANSPARENT),
        floatArrayOf(0f, 0.8f, 1f),
        Shader.TileMode.CLAMP
    )

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Background gradient
        val bg = LinearGradient(
            0f, 0f, 0f, height.toFloat(),
            Color.parseColor("#1e3c72"),
            Color.parseColor("#2a5298"),
            Shader.TileMode.CLAMP
        )
        paint.shader = bg
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
        paint.shader = null

        // Ball
        if (!gameOver && !gameWin) {
            paint.shader = ballShader
            canvas.drawCircle(ballX, ballY, ballRadius, paint)
            paint.shader = null
        }

        // Paddle
        paint.color = Color.parseColor("#00c6ff")
        paint.setShadowLayer(15f, 0f, 0f, Color.CYAN)
        canvas.drawRoundRect(
            paddleX,
            paddleY,
            paddleX + paddleWidth,
            paddleY + paddleHeight,
            20f, 20f, paint
        )
        paint.setShadowLayer(0f, 0f, 0f, Color.TRANSPARENT)

        // Score banner
        paint.color = Color.WHITE
        paint.textSize = 60f
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
        paint.setShadowLayer(8f, 0f, 0f, Color.BLACK)
        canvas.drawText("Score: $score", 50f, 100f, paint)
        paint.setShadowLayer(0f, 0f, 0f, Color.TRANSPARENT)

        // Game over / win screen
        if (gameOver || gameWin) {
            paint.color = Color.WHITE
            paint.textSize = 100f
            paint.setShadowLayer(15f, 0f, 0f, Color.BLACK)
            if (gameOver) canvas.drawText("GAME OVER", width / 2f - 260f, height / 2f, paint)
            else if (gameWin) canvas.drawText("YOU WIN!", width / 2f - 220f, height / 2f, paint)

            paint.textSize = 50f
            canvas.drawText("Tap to Restart", width / 2f - 150f, height / 2f + 100f, paint)
            paint.setShadowLayer(0f, 0f, 0f, Color.TRANSPARENT)
        } else {
            updateBall()
            invalidate()
        }
    }

    private fun updateBall() {
        ballX += ballSpeedX
        ballY += ballSpeedY

        // Rebuild shader
        ballShader = RadialGradient(
            ballX, ballY, ballRadius * 1.5f,
            intArrayOf(Color.RED, Color.YELLOW, Color.TRANSPARENT),
            floatArrayOf(0f, 0.8f, 1f),
            Shader.TileMode.CLAMP
        )

        // Wall collision
        if (ballX - ballRadius < 0 || ballX + ballRadius > width) ballSpeedX = -ballSpeedX
        if (ballY - ballRadius < 0) ballSpeedY = -ballSpeedY

        // Paddle collision
        if (ballY + ballRadius >= paddleY &&
            ballX in paddleX..(paddleX + paddleWidth)
        ) {
            ballSpeedY = -ballSpeedY
            score++

            if (score % 5 == 0) {
                level++
                if (level > 10) {
                    gameWin = true
                    return
                }
                if (ballSpeedY > 0) ballSpeedY += 2 else ballSpeedY -= 2
                if (ballSpeedX > 0) ballSpeedX += 2 else ballSpeedX -= 2
            }

            ballSpeedX += Random.nextInt(-2, 3)
            if (ballSpeedX == 0f) ballSpeedX = 5f
        }

        if (ballY - ballRadius > height) gameOver = true
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if ((gameOver || gameWin) && event.action == MotionEvent.ACTION_DOWN) {
            // Reset game
            score = 0
            level = 1
            ballX = Random.nextInt(100, width - 100).toFloat()
            ballY = 200f
            ballSpeedX = 8f
            ballSpeedY = 12f
            gameOver = false
            gameWin = false
            invalidate()
            return true
        }

        if (event.action == MotionEvent.ACTION_MOVE) {
            paddleX = event.x - paddleWidth / 2
            if (paddleX < 0) paddleX = 0f
            if (paddleX + paddleWidth > width) paddleX = (width - paddleWidth)
            invalidate()
        }
        return true
    }

    fun setInitialSpeed(speedX: Float, speedY: Float) {
        ballSpeedX = speedX
        ballSpeedY = speedY
    }

}
