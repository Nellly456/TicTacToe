/*
 * Implements the Tic Tac Toe Web Service.
 */
package com.tttws;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

/*
 * Tic Tac Toe Web Service
 */

/**
 *
 * @author petar
 */

@WebService(serviceName = "TicTacToeWebService")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class TicTacToeWS {
    
    private final TTT_MySQL dao; 
    
    /**
     * Web Service constructor
     */
    public TicTacToeWS() {
        dao = new TTT_MySQL("localhost", "root", "", "tictactoe");
    }

    /**
     * Used to login to the system
     * @param username the username of the user logging in.
     * @param password the password of the user logging in.
     * @return either 0 if incorrect details, an integer greater than 0 (which is the users id) if correct details, -1 if there is a general error.
     */
    @WebMethod(operationName = "login")
    public int login(@WebParam(name = "username") String username, @WebParam(name = "password") String password) {
        try {
            String sqlCmd = "SELECT autokey FROM users WHERE username = '" + username;
            sqlCmd += "' AND password = PASSWORD('" + password + "') AND isactive = 1;";
            String result = dao.retrieve(sqlCmd);
            if("ERROR".equals(result)) {
                return 0;
            } else {
                return Integer.parseInt(result);
            }
        } catch(Exception e) {
            System.out.println("Error is : " + e.getMessage());
            return -1;
        }
    }

    /**
     * Used to register a new user on the system.
     * @param username the username of the new user.
     * @param password the password of the new user.
     * @param name - the name of the new user.
     * @param surname the surname of the new user.
     * @return The user provides their username, password, name and surname. If successful, the method returns the users id on the system (the autokey 
     * field from the users table). If unsuccessful, it returns either “ERROR-REPEAT” if a user with that username already exists, “ERROR-INSERT” if the 
     * method could not add the data to the users table, “ERROR-RETRIEVE “ if the method cannot retrieve the newly inserted data from the users table, or
     * “ERROR-DB” if the method cannot find the DB.
     */
    @WebMethod(operationName = "register")
    public String register(@WebParam(name = "username") String username, @WebParam(name = "password") String password, @WebParam(name = "name") String name, @WebParam(name = "surname") String surname) {
        String sqlCmd = "";
        
        sqlCmd = "SELECT COUNT(*) FROM users WHERE username = '" + username + "';";
        try {
            String result = "";
            result = dao.retrieve(sqlCmd);
            if(result.equals("0")) {
                sqlCmd = "INSERT INTO users VALUES (default, '";
                sqlCmd += name + "', '" + surname + "', '" + username + "', PASSWORD('";
                sqlCmd += password + "'), 1, default);";
                System.out.println(sqlCmd);
                try {
                    result = dao.insert(sqlCmd);
                    if(!"INSERTED 1 ROWS.".equals(result)) {
                        return "ERROR-INSERT";
                    } else {
                        sqlCmd = "SELECT autokey FROM users WHERE username = '" + username;
                        sqlCmd += "' AND password = PASSWORD('" + password + "') AND isactive = 1;";

                        try {
                            result = dao.retrieve(sqlCmd);
                            return result;
                        } catch(Exception g) {
                            return "ERROR-RETRIEVE";
                        }
                    }
                } catch(Exception e) {
                    return "ERROR-DB";
                }
            } else {
                return "ERROR-REPEAT";
            }
        } catch(Exception u) {
            return "ERROR-DB";
        }        
    }

    /**
     * Used to create a new game.
     * @param uid the user id of the user creating the new game.
     * @return either the id of the new game (returned as a string), or "ERROR-NOTFOUND", "ERROR-RETRIEVE", or "ERROR-INSERT" if the game couldn't be created, or "ERROR-DB" if unable to talk to the DB or DBMS.
     */
    @WebMethod(operationName = "newGame")
    public String newGame(@WebParam(name = "uid") int uid) {
        String sqlCmd = "INSERT INTO games VALUES(default, " + uid + ", NULL, -1, default);";
        try {
            String result = dao.insert(sqlCmd);
            if(result.equals("INSERTED 1 ROWS.")) {
                sqlCmd = "SELECT autokey FROM games WHERE gamestate = -1 AND p1 = " + uid + " AND p2 IS NULL ORDER BY autokey DESC LIMIT 1;";
                try {
                    result = dao.retrieve(sqlCmd);
                    if(result.length() > 0) {
                        return result;
                    } else {
                        return "ERROR-NOTFOUND";
                    }
                } catch(Exception g) {
                    return "ERROR-RETRIEVE";
                }
            } else {
                return "ERROR-INSERT";
            }
        } catch(Exception e) {
            return "ERROR-DB";
        }
    }

    /**
     * Used to join an existing game.
     * @param uid the user id of the person joining the game.
     * @param gid the game id of the game the player wants to join.
     * @return 1 (as a string) if able to join, 0 (as a string) if unable to join, or "ERROR-DB" if unable to talk to the DB or DBMS.
     */
    @WebMethod(operationName = "joinGame")
    public String joinGame(@WebParam(name = "uid") int uid, @WebParam(name = "gid") int gid) {
        String sqlCmd = "UPDATE games SET p2 = " + uid + ", gamestate = 0 WHERE autokey = " + gid + ";";
        try {
            String result = dao.update(sqlCmd);
            if(result.equals("UPDATED 1 ROWS.")) {
                return "1";
            } else {
                return "0";
            }
        } catch(Exception e) {
            return "ERROR-DB";
        }
    }
    
    /**
     * Returns the current board as a formatted string.
     * @param gid the game id of the game whose board we want to see.
     * @return Used to return all moves taken to date for a particular game. If successful, the method returns the moves taken as a string as follows {pId, x, y} for each row, each row separated by \n. The moves are returned in order of play (i.e. first move will be first row returned etc.). Using this we can evaluate how many moves have been taken (i.e. the number of rows), the last player to take a move (pId of the final row), plus all the moves taken to date. 
     * The method returns "ERROR-NOMOVES" if no moves have yet been made for that game, or "ERROR-DB" if unable to talk to the DB or DBMS.
     */
    @WebMethod(operationName = "getBoard")
    public String getBoard(@WebParam(name = "gid") int gid) {
        String sqlCmd = "SELECT pId, x, y FROM moves WHERE gId = " + gid + " ORDER BY played ASC;";
        try {
            String result = dao.retrieve(sqlCmd);
            if(result.length() > 0) {
                return result;
            } else {
                return "ERROR-NOMOVES";
            }
        } catch(Exception e) {
            return "ERROR-DB";
        }
    }

    /**
     * Returns the state of the game.
     * @param gid the game id of the game whose state we wish to check.
     * @return -1 (as a string) if waiting for a second player to join, 0 (as a string) if the game is in progress, 1 (as a string) if player 1 has won, 
     * 2 (as a string) if player 2 has won, 3 (as a string) if a draw, or "ERROR-NOGAME" if a game matching the game id cannot be found, or "ERROR-DB" if
     * unable to talk to the DB or DBMS.
     */
    @WebMethod(operationName = "getGameState")
    public String getGameState(@WebParam(name = "gid") int gid) {
        String sqlCmd = "SELECT gamestate FROM games WHERE autokey = " + gid + ";";
        try {
            String result = dao.retrieve(sqlCmd);
            if(result.length() > 0) {
                return result;
            } else {
                return "ERROR-NOGAME";
            }
        } catch(Exception e) {
            return "ERROR-DB";
        }
    }

    /**
     * Sets the state of a game.
     * @param gid the game id of the game whose state we wish to set.
     * @param gstate the state we wish to set the game to.
     * @return 1 (as a string) if successful, "ERROR-NOGAME" if unable to find a game matching gid, "ERROR-DB" if unable to talk the the DB or DBMS.
     */
    @WebMethod(operationName = "setGameState")
    public String setGameState(@WebParam(name = "gid") int gid, @WebParam(name = "gstate") int gstate) {
        String sqlCmd = "UPDATE games SET gamestate = " + gstate + " WHERE autokey = " + gid + ";";
        try {
            String result = dao.update(sqlCmd);
            if(result.equals("UPDATED 1 ROWS.")) {
                return "1";
            } else {
                return "ERROR-NOGAME";
            }
        } catch(Exception e) {
            return "ERROR-DB";
        }
    }

    /**
     * Checks a particular square in a game to see if it has been or can be taken.
     * @param x the columns position of the square.
     * @param y the row position of the square.
     * @param gid the game we are playing/checking.
     * @return 1 (as a string) if the square is taken, 0 (as a string) if the square is available, "ERROR-DB" if there is a problem talking to the DB or DBMS.
     */
    @WebMethod(operationName = "checkSquare")
    public String checkSquare(@WebParam(name = "x") int x, @WebParam(name = "y") int y, @WebParam(name = "gid") int gid) {
        String sqlCmd = "SELECT COUNT(*) FROM moves WHERE x = " + x + " AND y = " + y + " AND ";
        sqlCmd += " gId = " + gid + ";";
        try {
            String result = dao.retrieve(sqlCmd);
            return result;
        } catch (Exception e) {
            return "ERROR-DB";
        }
    }

    /**
     * Takes a square in a particular game for a particular player.
     * @param x the column position of the square.
     * @param y the row position of the square.
     * @param gid the game we are playing.
     * @param pid the user id of the player taking the square.
     * @return 1 (as a string) if successful, 0 (as a string) if unsuccessful, "ERROR-TAKEN" if the square is unavailable, "ERROR-DB" if there is a problem
     * talking to the DB or DBMS, "ERROR" if there is a general error.
     */
    @WebMethod(operationName = "takeSquare")
    public String takeSquare(@WebParam(name = "x") int x, @WebParam(name = "y") int y, @WebParam(name = "gid") int gid, @WebParam(name = "pid") int pid) {
        int count = Integer.parseInt(checkSquare(x, y, gid));
        String flag = "";
        switch(count) {
            case 1:
                flag = "ERROR-TAKEN";
            break;
            case 0:
                String sqlCmd = "INSERT INTO moves VALUES(default, " + pid + ", " + x + ", " + y;
                sqlCmd += ", default, " + gid + ");";
                try {
                    String result = dao.insert(sqlCmd);
                    if(result.equals("INSERTED 1 ROWS.")) {
                        flag = "1";
                    } else {
                        flag = "0";
                    }
                } catch(Exception e) {
                    flag = "ERROR-DB";
                }
            break;
            default: 
                flag = "ERROR";
        }
        return flag;
    }

    /**
     * Used to see if the game is over or not, and who has won if it is over.
     * @param gid the game id of the game we are checking.
     * @return 0 (as a string) if the game hasn’t been won but can continue to be played, 1 (as a string) if player 1 has won, 
     * 2 (as a string) if player 2 has won, or 3 (as a string) if the game is a draw. The method will return “ERROR-RETRIEVE” if there is an issue 
     * getting details about the game, “ERROR-NOGAME” if no game can be found matching the gid, “ERROR-DB” if there is a problem accessing the DB or DBMS.
     */
    @WebMethod(operationName = "checkWin")
    public String checkWin(@WebParam(name = "gid") int gid) {
        String result = getBoard(gid);
        int winner = 0;
        if(result.length() > 0) {
            String[] rows = result.split("\n");
            int num_moves = rows.length;
            int[][] table = new int[3][3];
            for(int a = 0; a < 3; a++) {
                for(int b = 0; b < 3; b++) {
                    table[a][b] = 0;
                }
            }
            for(int i = 0; i < num_moves; i++) {
                String[] cells = rows[i].split(",");
                int pid = Integer.parseInt(cells[0]);
                int x = Integer.parseInt(cells[1]);
                int y = Integer.parseInt(cells[2]);
                table[x][y] = pid;
            }
            
            //check rows and cols
            for(int t = 0; t < 3; t++) {
                if(table[t][0] == table[t][1] && table[t][1] == table[t][2] && table[t][0] != 0) {
                    winner = table[t][0];
                }
                if(table[0][t] == table[1][t] && table[1][t] == table[2][t] && table[0][t] != 0) {
                    winner = table[0][t];
                }
            }
            
            //Check diagonals
            if(table[0][0] == table[1][1] && table[1][1] == table[2][2] && table[0][0] != 0) {
                winner = table[0][0];
            }
            if(table[0][2] == table[1][1] && table[1][1] == table[2][0] && table[0][2] != 0) {
                winner = table[0][2];
            }
            
            if(winner > 0) {
                try {
                    // check is winner p1 or p2;
                    String sqlCmd = "SELECT * FROM games WHERE autokey = " + gid + ";";
                    String gDetails = dao.retrieve(sqlCmd);
                    if(gDetails.length() > 0) {
                        String[] details = gDetails.split(",");
                        if(details.length > 0) {
                            int p1 = Integer.parseInt(details[1]);
                            int p2 = Integer.parseInt(details[2]);
                            if(winner == p1) {
                                return "1";
                            } else {
                                return "2";
                            }
                        } else {
                            return "ERROR-RETRIEVE";
                        }
                    } else {
                        return "ERROR-RETRIEVE";
                    }
                } catch(Exception e) {
                    return "ERROR-DB";
                }
            } else {
                if(num_moves < 9) {
                    return "0";
                } else {
                    return "3";
                }
            }
        } else {
            return "ERROR-NOGAME";
        }
    }

    /**
     * Used to remove un-started games from the DB.
     * @param gid the game id of the game you wish to remove.
     * @param uid the user id of the person making the request (can only delete games you created).
     * @return If the game has already started or the user is not the creator of the game, the method returns “ERROR-GAMESTARTED”, 
     * otherwise it returns 1 (as a string). The method returns “ERROR-DB” if it cannot access the DBMS.
     */
    @WebMethod(operationName = "deleteGame")
    public String deleteGame(@WebParam(name = "gid") int gid, @WebParam(name = "uid") int uid) {
        String sqlCmd = "SELECT COUNT(*) FROM games WHERE autokey = " + gid + " AND p1 = ";
        sqlCmd += uid + " AND p2 IS NULL;";
        try {
            String result = dao.retrieve(sqlCmd);
            if(result.equals("1")) {
                sqlCmd = "DELETE FROM games WHERE autokey = " + gid + ";";
                result = dao.remove(sqlCmd);
                return "1";
            } else {
                return "ERROR-GAMESTARTED";
            }
        } catch(Exception e) {
            return "ERROR-DB";
        }
    }

    /**
     * Used to show games you have created but that have not yet started.
     * @param uid the user id of the player making the request.
     * @return If the method is successful it returns a string as follows {{g.autokey, u.username, g.started}\n}*. If unsuccessful, the method returns 
     * “ERROR-NOGAMES” if it can find no games, or “ERROR-DB” if it cannot access the DBMS.
     */
    @WebMethod(operationName = "showMyOpenGames")
    public String showMyOpenGames(@WebParam(name = "uid") int uid) {
        String sqlCmd = "SELECT g.autokey, u.username, g.started FROM games g, users u WHERE g.p1 = " + uid + " AND g.p2 IS NULL AND g.p1 = u.autokey ORDER BY g.started ASC;";
        try {
            String result = dao.retrieve(sqlCmd);
            if(result.length() > 0) {
                return result;
            } else {
                return "ERROR-NOGAMES";
            }
        } catch(Exception e) {
            return "ERROR-DB";
        }
    }

    /**
     * Retrieves all games the user has played/is playing.
     * @param uid the user id of the player making the request.
     * @return If successful it returns a string as follows {{g.autokey, h.username, o.username, g.started}\n}*. h.username and o.username are the 
     * usernames of player 1 and player 2 respectively. If unsuccessful, the method returns “ERROR-NOGAMES” if it can find no games, or “ERROR-DB” 
     * if it cannot access the DBMS.
     */
    @WebMethod(operationName = "showAllMyGames")
    public String showAllMyGames(@WebParam(name = "uid") int uid) {
        String sqlCmd = "SELECT g.autokey, h.username, o.username, g.started FROM games g, users h, users o WHERE (g.p1 = " + uid + " OR g.p2 = " + uid + ") AND g.p1 = h.autokey AND g.p2 = o.autokey ORDER BY g.started ASC;";
        try {
            String result = dao.retrieve(sqlCmd);
            if(result.length() > 0) {
                return result;
            } else {
                return "ERROR-NOGAMES";
            }
        } catch(Exception e) {
            return "ERROR-DB";
        }
    }

    /**
     * Used to generate the content for a league table.
     * @return If successful it returns a string as follows {{g.autokey, h.username, o.username, g.gamestate, g.started}\n}*. h.username and o.username 
     * are the usernames of player 1 and player 2 respectively. If unsuccessful, the method returns “ERROR-NOGAMES” if it can find no games, or “ERROR-DB”
     * if it cannot access the DBMS.
     */
    @WebMethod(operationName = "leagueTable")
    public String leagueTable() {
        String sqlCmd = "SELECT g.autokey, h.username, o.username, g.gamestate, g.started FROM games g, users h, users o WHERE g.p1 = h.autokey AND g.p2 = o.autokey ORDER BY g.started ASC;";
        try {
            String result = dao.retrieve(sqlCmd);
            if(result.length() > 0) {
                return result;
            } else {
                return "ERROR-NOGAMES";
            }
        } catch(Exception e) {
            return "ERROR-DB";
        }
    }

    /**
     * The list of open games (those with a p1 but no p2).
     * @return If successful it returns a string as follows {{g.autokey, h.username, g.started}\n}*. h.username is the usernames of the player who started
     * the game. If unsuccessful, the method returns “ERROR-NOGAMES” if it can find no games, or “ERROR-DB” if it cannot access the DBMS.
     */
    @WebMethod(operationName = "showOpenGames")
    public String showOpenGames() {
        String sqlCmd = "SELECT g.autokey, h.username, g.started FROM games g, users h WHERE g.p2 IS NULL AND g.p1 = h.autokey ORDER BY g.started ASC;";
        try {
            String result = dao.retrieve(sqlCmd);
            if(result.length() > 0) {
                return result;
            } else {
                return "ERROR-NOGAMES";
            }
        } catch(Exception e) {
            return "ERROR-DB";
        }
    }
    
    /**
     * Closes the connection to the database (before exiting the application).
     */
    @WebMethod(operationName = "closeConnection")
    public void closeConnection() {
        dao.close();
    }
}
