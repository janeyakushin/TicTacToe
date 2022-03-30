angular.module('gameplay', ['ngStorage']).controller('indexController', function($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:8080/gameplay';

    $scope.newgame = function () {
        $scope.auth = true;
        $scope.win = false;
        $scope.deadHeat = false;
        $scope.gameok = false;
        $scope.nextstep = true;
        $scope.gameslist = null;
    };

    $scope.newgameplay = function () {
        $scope.newgame();
        $scope.name1 = "";
        $scope.name2 = "";
    };

    $scope.newgame();

    $scope.setPlayers = function () {
        $http({
            url: contextPath,
            method: 'GET',
        }).then(function (response) {
            $scope.field = response.data;
        });

        $http({
            url: contextPath + '/players/',
            method: 'POST',
            params: {
                name1: $scope.name1,
                name2: $scope.name2,
            }
        }).then(function (response) {
            if (response.data[0] == "double") {
                alert("Выбирайте разные имена!");
                $scope.name1 = "";
                $scope.name2 = "";
                return;
            }
            $scope.auth = false;
            $scope.symbols = response.data;
            $scope.name = $scope.name1;
        });
    };

    $scope.setStep = function (x, y) {
        if ($scope.win) {
            return;
        }
       $http({
            url: contextPath + '/step/',
            method: 'POST',
            params: {
                name: $scope.name,
                x: x,
                y: y
            }
        }).then(function (response) {
            $scope.field = response.data.field;
            isWin();
            $scope.name = response.data.stepPlayerName;
       });

    };

    isWin = function () {
       $http({
            url: contextPath + '/win/',
            method: 'GET',
        }).then(function (response) {
            $scope.win = response.data.data;
            if ($scope.win == "win") {
                $scope.win_player = response.data.playerWin;
                $scope.win = true;
            } else if ($scope.win == "deadHeat") {
                $scope.win = false;
                $scope.deadHeat = true;
            } else {
                $scope.win = false;
                $scope.deadHeat = false;
            }
            rating();
        });
    };

    rating = function () {
       $http({
            url: contextPath + '/rating/',
            method: 'GET',
        }).then(function (response) {
            $scope.rating = response.data;
        });
    };

    $scope.playgame = function () {
       $http({
            url: contextPath + '/games/',
            method: 'GET',
        }).then(function (response) {
            $scope.gameslist = response.data;
        });
    };

    $scope.gameforplay = function (game) {
       $http({
            url: contextPath + '/game/',
            method: 'GET',
            params: {
                game: game
            }
         }).then(function (response) {
            $scope.gameok = response.data;
            if(!$scope.gameok) {
                alert ("Запись игры не доступна!")
                return;
            }
            game_players();
        });
        $http({
            url: contextPath + '/game/init/',
            method: 'GET',
        }).then(function (response) {
            $scope.field = response.data;
            $scope.step = 0;
        });

   };

   game_players = function() {
       $http({
           url: contextPath + '/game/players/',
           method: 'GET',
       }).then(function (response) {
           $scope.name1 = response.data[0];
           $scope.name2 = response.data[1];
       });
   }

   $scope.tostep = function(st) {
          $scope.step = $scope.step + st;
          $http({
               url: contextPath + '/game/step/',
               method: 'GET',
               params: {
                   step: $scope.step
               }
            }).then(function (response) {
            if(response.data[0][0] == "q") {
                $scope.nextstep = false;
                return;
            } else {
                $scope.nextstep = true;
            }
               $scope.field = response.data;
           });
    }

    $rootScope.isUserLoggedIn = function () {
            return false;
    };


});