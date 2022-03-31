package ru.yajaneya.tictactoe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yajaneya.tictactoe.services.PlayService;

import java.util.List;

@RestController
@Tag(name="API воспроизведения записанной игры 'Крестики-нолики'", description = "Методы работы с бэк-эндом воспроизведения записанной игры")
@RequiredArgsConstructor
public class PlayController {
    private final PlayService playService;

    @Operation(summary = "Запрос на получение списка записанных игр")
    @GetMapping("/games")
    public List<String> getGames () {
        return playService.getGames();
    }

    @Operation(summary = "Запрос на инициализацию данных записанной игры")
    @GetMapping ("/game")
    public boolean getGame (@RequestParam String game) {
        return playService.getGame(game);
    }

    @Operation(summary = "Запрос на получение имен игроков и символов, им соответствующих, из записанной игры")
    @GetMapping ("/game/players")
    public List<String> getPlayers () {
        return playService.getPlayers();
    }

    @Operation(summary = "Запрос на получение пустого игрового поля")
    @GetMapping ("/game/init")
    public char[][] init () {
        return playService.init();
    }

    @Operation(summary = "Запрос на получение поля, соответствующего определенному шагу игры")
    @GetMapping ("/game/step")
    public char[][] getStep (@RequestParam int step) {
        return playService.getStep(step);
    }

}
