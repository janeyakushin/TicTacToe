package ru.yajaneya.tictactoe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yajaneya.tictactoe.dtos.StepDto;
import ru.yajaneya.tictactoe.models.*;
import ru.yajaneya.tictactoe.services.GameService;

import java.util.List;

@RestController
@Tag(name="API игры 'Крестики-нолики'", description = "Методы работы с бэк-эндом игры")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    @Operation(summary = "Запрос на получение пустого игрового поля")
    @GetMapping
    public char[][] init () {
        return gameService.init();
    }

    @Operation(summary = "Запрос на проверку выигрыша")
    @GetMapping ("/win")
    public RequestWin isWin () {
       return gameService.isWin();
    }

    @Operation(summary = "Запрос на получение рейтинга игроков")
    @GetMapping ("/rating")
    public List<Player> getRating () {
        return gameService.getRating();
    }

    @Operation(summary = "Запрос на отправку имён игроков")
    @PostMapping ("/players")
    public String [] setPlayers (@RequestParam String name1, @RequestParam String name2) {
        return gameService.setPlayers(name1, name2);
    }

    @Operation(summary = "Запрос на отправку игрового хода игрока")
    @PostMapping ("/step")
    public StepDto setStep (@RequestParam String name, @RequestParam int x, @RequestParam int y) {
        return gameService.setStep(name, x, y);
    }
}
