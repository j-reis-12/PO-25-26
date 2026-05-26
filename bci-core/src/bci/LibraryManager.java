package bci;

import bci.exceptions.*;
import bci.user.*;
import bci.work.*;
import bci.work.category.*;
import bci.work.creator.*;
import bci.request.*;
import bci.request.rules.*;
import bci.notification.*;

import java.io.*;
import java.util.List;

/**
 * The façade class.
 */
public class LibraryManager {
    /** The default rules for library requests. */
    private RequestRule _defaultRules = new AndRule(
      new NotDuplicatedRequest(),
      new NotUserSuspended(),
      new NotWorkUnavailable(),
      new NotRequestLimitExceeded(),
      new NotReferenceWork(),
      new NotPriceLimitExceeded());

    /** The object doing all the actual work. */
    private Library _library = new Library(_defaultRules);

    public LibraryManager() { }

    /**
     * Checks if changes have been made to the library since the last save.
     * @return true if changes have been made to the library.
     */
    public boolean changed() {
      return (_library != null) ? _library.changed() : false;
    }
    
    public String getFilename() { return _library.getFilename(); }
    public void setFilename(String filename) { _library.setFilename(filename); }

    /**
     * Saves the current library to the associated file.
     * @throws MissingFileAssociationException if no file is associated with the current library.
     * @throws IOException if some I/O problem occurs.
     */
    public void save() throws MissingFileAssociationException, IOException {
      String filename = getFilename();
      if (filename.isEmpty()) throw new MissingFileAssociationException();

      try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)))) {
        oos.writeObject(_library);
      } catch (FileNotFoundException e) { throw new IOException(); }
      _library.setChanged(false);
    }

    /**
     * Saves the current library to the given file and associates the file with it.
     * @param filename name of the file where to save the current library.
     * @throws MissingFileAssociationException if no file is associated with the current library.
     * @throws IOException if some I/O problem occurs.
     */
    public void saveAs(String filename) throws MissingFileAssociationException, IOException {
        setFilename(filename);
        save();
    }

    /**
     * Loads a library from the given file and associates the file with it.
     * @param filename name of the file where to load the library from.
     * @throws UnavailableFileException if some problem occurs while trying to read the file.
     */
    public void load(String filename) throws UnavailableFileException {
      try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(
      new FileInputStream(filename)))) {
        _library = (Library) ois.readObject();
        setFilename(filename);
        _library.setChanged(false);
      }
      catch (ClassNotFoundException | IOException e) { throw new UnavailableFileException(filename); }
    }

    /**
     * Read text input file and initializes the current library (which should be empty)
     * with the domain entities representeed in the import file.
     *
     * @param filename name of the text input file
     * @throws ImportFileException if some error happens during the processing of the
     * import file.
     */
    public void importFile(String filename) throws ImportFileException {
      Library temp = new Library(_defaultRules);
      
      if (filename != null && !filename.isEmpty()) try { temp.importFile(filename); }
        catch (IOException | UnrecognizedEntryException | MaxCreatorsException | UnknownCategoryException | InvalidIsbnException | InvalidUserException e) { throw new ImportFileException(filename, e); }
      
      _library = temp;
    }

    // All the methods below are just redirections to the correspondent Library methods.

    /**
     * @see bci.Library#getDate()
     */
    public int getDate() { return _library.getDate(); }

    /**
     * @see bci.Library#advanceDate(int)
     */
    public void advanceDate(int days) { if (days > 0) _library.advanceDate(days); }

    /**
     * @see bci.Library#registerUser(String, String)
     */
    public int registerUser(String name, String email) throws InvalidUserException { return _library.registerUser(name, email); }

    /**
     * @see bci.Library#getUser(int)
     */
    public User getUser(int id) throws UserNotFoundException { return _library.getUser(id); }

    /**
     * @see bci.Library#getWork(int)
     */
    public Work getWork(int id) throws WorkNotFoundException { return _library.getWork(id); }

    /**
     * @see bci.Library#getCreator(String)
     */
    public Creator getCreator(String name) throws CreatorNotFoundException { return _library.getCreator(name); }

    /**
     * @see bci.Library#selectUsers(Selector)
     */
    public List<User> selectUsers(Selector<User> selector) { return _library.selectUsers(selector); }

    /**
     * @see bci.Library#selectWorks(Selector)
     */
    public List<Work> selectWorks(Selector<Work> selector) { return _library.selectWorks(selector); }

    /**
     * @see bci.Library#selectWorksByCreator(String name)
     */
    public List<Work> selectWorksByCreator(String name) throws CreatorNotFoundException { return _library.selectWorksByCreator(name); }

    /**
     * @see bci.Library#updateWorkInventory(int, int)
     */
    public void updateWorkInventory(int id, int amount) throws WorkNotFoundException, InvalidUpdateAmountException { _library.updateWorkInventory(id, amount); }

    /**
     * @see bci.Library#searchWorks(String)
     */
    public List<Work> searchWorks (String search) { return _library.searchWorks(search); }

    /**
     * @see bci.Library#requestWork(int, int)
     */
    public int requestWork(int userId, int workId) throws InvalidRequestException, UserNotFoundException, WorkNotFoundException { return _library.requestWork(userId, workId); }

    /**
     * @see bci.Library#returnWork(int, int)
     */
    public int returnWork(int userId, int workId) throws NotBorrowedWorkException, UserNotFoundException, WorkNotFoundException, NotBorrowedWorkException { return _library.returnWork(userId, workId); }

    /**
     * @see bci.Library#payUserFine(int, int)
     */
    public void payUserFine(int userId, int amount) throws UserNotFoundException { _library.payUserFine(userId, amount); }

    /**
     * @see bci.Library#updateUserState(int)
     */
    public void updateUserState(int userId) throws UserNotFoundException { _library.updateUserState(userId); }

    /**
     * @see bci.Library#clearUserFine(int)
     */
    public void clearUserFine(int userId) throws UserNotFoundException, UserNotSuspendedException { _library.clearUserFine(userId); }

    /**
     * @see bci.Library#getUserNotifications(int)
     */
    public List<Notification> getUserNotifications(int userId) throws UserNotFoundException { return _library.getUserNotifications(userId); }

    /**
     * @see bci.Library#clearUserNotifications(int)
     */
    public void clearUserNotifications(int userId) throws UserNotFoundException { _library.clearUserNotifications(userId); }

    /**
     * @see bci.Library#setWorkReturnNotification(int)
     */
    public void setWorkReturnNotification(int userId, int workId) throws UserNotFoundException, WorkNotFoundException { _library.setWorkReturnNotification(userId, workId); }
}
