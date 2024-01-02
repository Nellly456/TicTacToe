
package com.tttws;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.xml.ws.Action;


/**
 * This class was generated by the XML-WS Tools.
 * XML-WS Tools 4.0.1
 * Generated source version: 3.0
 * 
 */
@WebService(name = "TicTacToeWS", targetNamespace = "http://tttws.com/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface TicTacToeWS {


    /**
     * 
     * @param name
     * @param password
     * @param surname
     * @param username
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(partName = "return")
    @Action(input = "http://tttws.com/TicTacToeWS/registerRequest", output = "http://tttws.com/TicTacToeWS/registerResponse")
    public String register(
        @WebParam(name = "username", partName = "username")
        String username,
        @WebParam(name = "password", partName = "password")
        String password,
        @WebParam(name = "name", partName = "name")
        String name,
        @WebParam(name = "surname", partName = "surname")
        String surname);

    /**
     * 
     * @param uid
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(partName = "return")
    @Action(input = "http://tttws.com/TicTacToeWS/showAllMyGamesRequest", output = "http://tttws.com/TicTacToeWS/showAllMyGamesResponse")
    public String showAllMyGames(
        @WebParam(name = "uid", partName = "uid")
        int uid);

    /**
     * 
     * @param uid
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(partName = "return")
    @Action(input = "http://tttws.com/TicTacToeWS/showMyOpenGamesRequest", output = "http://tttws.com/TicTacToeWS/showMyOpenGamesResponse")
    public String showMyOpenGames(
        @WebParam(name = "uid", partName = "uid")
        int uid);

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(partName = "return")
    @Action(input = "http://tttws.com/TicTacToeWS/showOpenGamesRequest", output = "http://tttws.com/TicTacToeWS/showOpenGamesResponse")
    public String showOpenGames();

    /**
     * 
     * @param password
     * @param username
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(partName = "return")
    @Action(input = "http://tttws.com/TicTacToeWS/loginRequest", output = "http://tttws.com/TicTacToeWS/loginResponse")
    public int login(
        @WebParam(name = "username", partName = "username")
        String username,
        @WebParam(name = "password", partName = "password")
        String password);

    /**
     * 
     * @param gid
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(partName = "return")
    @Action(input = "http://tttws.com/TicTacToeWS/getBoardRequest", output = "http://tttws.com/TicTacToeWS/getBoardResponse")
    public String getBoard(
        @WebParam(name = "gid", partName = "gid")
        int gid);

    /**
     * 
     * @param gid
     * @param x
     * @param y
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(partName = "return")
    @Action(input = "http://tttws.com/TicTacToeWS/checkSquareRequest", output = "http://tttws.com/TicTacToeWS/checkSquareResponse")
    public String checkSquare(
        @WebParam(name = "x", partName = "x")
        int x,
        @WebParam(name = "y", partName = "y")
        int y,
        @WebParam(name = "gid", partName = "gid")
        int gid);

    /**
     * 
     */
    @WebMethod
    @Action(input = "http://tttws.com/TicTacToeWS/closeConnectionRequest", output = "http://tttws.com/TicTacToeWS/closeConnectionResponse")
    public void closeConnection();

    /**
     * 
     * @param gid
     * @param uid
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(partName = "return")
    @Action(input = "http://tttws.com/TicTacToeWS/deleteGameRequest", output = "http://tttws.com/TicTacToeWS/deleteGameResponse")
    public String deleteGame(
        @WebParam(name = "gid", partName = "gid")
        int gid,
        @WebParam(name = "uid", partName = "uid")
        int uid);

    /**
     * 
     * @param gid
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(partName = "return")
    @Action(input = "http://tttws.com/TicTacToeWS/checkWinRequest", output = "http://tttws.com/TicTacToeWS/checkWinResponse")
    public String checkWin(
        @WebParam(name = "gid", partName = "gid")
        int gid);

    /**
     * 
     * @param gid
     * @param pid
     * @param x
     * @param y
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(partName = "return")
    @Action(input = "http://tttws.com/TicTacToeWS/takeSquareRequest", output = "http://tttws.com/TicTacToeWS/takeSquareResponse")
    public String takeSquare(
        @WebParam(name = "x", partName = "x")
        int x,
        @WebParam(name = "y", partName = "y")
        int y,
        @WebParam(name = "gid", partName = "gid")
        int gid,
        @WebParam(name = "pid", partName = "pid")
        int pid);

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(partName = "return")
    @Action(input = "http://tttws.com/TicTacToeWS/leagueTableRequest", output = "http://tttws.com/TicTacToeWS/leagueTableResponse")
    public String leagueTable();

    /**
     * 
     * @param gid
     * @param gstate
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(partName = "return")
    @Action(input = "http://tttws.com/TicTacToeWS/setGameStateRequest", output = "http://tttws.com/TicTacToeWS/setGameStateResponse")
    public String setGameState(
        @WebParam(name = "gid", partName = "gid")
        int gid,
        @WebParam(name = "gstate", partName = "gstate")
        int gstate);

    /**
     * 
     * @param uid
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(partName = "return")
    @Action(input = "http://tttws.com/TicTacToeWS/newGameRequest", output = "http://tttws.com/TicTacToeWS/newGameResponse")
    public String newGame(
        @WebParam(name = "uid", partName = "uid")
        int uid);

    /**
     * 
     * @param gid
     * @param uid
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(partName = "return")
    @Action(input = "http://tttws.com/TicTacToeWS/joinGameRequest", output = "http://tttws.com/TicTacToeWS/joinGameResponse")
    public String joinGame(
        @WebParam(name = "uid", partName = "uid")
        int uid,
        @WebParam(name = "gid", partName = "gid")
        int gid);

    /**
     * 
     * @param gid
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(partName = "return")
    @Action(input = "http://tttws.com/TicTacToeWS/getGameStateRequest", output = "http://tttws.com/TicTacToeWS/getGameStateResponse")
    public String getGameState(
        @WebParam(name = "gid", partName = "gid")
        int gid);

}
