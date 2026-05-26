package bci;

import bci.exceptions.*;
import bci.user.*;
import bci.work.*;
import bci.work.category.*;
import bci.work.creator.*;
import bci.request.*;
import bci.request.rules.*;
import bci.notification.*;
import bci.notification.WorkObserver.ObserverType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/** Class that represents the library as a whole. */
class Library implements Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    private Map<Integer, User> _users = new HashMap<>();
    private Map<Integer, Work> _works = new HashMap<>();
    private Map<String, Creator> _creators = new HashMap<>();
    private int _nextUserId = 1;
    private int _nextWorkId = 1;

    /** Request rules to be applied when making a request. */
    private RequestRule _rules = new AndRule();

    /** Current date in days since the library was first created. */
    private int _date = 1;
    
    /** Indicates if the library has suffered any changes (add/remove). */
    private transient boolean _changed = false;

    /* name of the file to save the library to */
    private String _filename = "";

    public Library() { /* empty constructor: no specifications at creation */ }

    /** If the default rules (in order) are specified. */
    public Library(RequestRule defaultRules) { _rules = defaultRules; }

    public boolean changed() { return _changed; }
    public void setChanged(boolean changed) { _changed = changed; }

    public String getFilename() { return _filename; }
    public void setFilename(String filename) { _filename = filename; }

    /**
     * Read the text input file at the beginning of the program and populates the
     * instances of the various possible types (books, DVDs, users).
     *
     * @param filename name of the file to load
     * @throws IOException
     * @throws UnrecognizedEntryException
     * @throws MaxCreatorsException
     * @throws UnknownCategoryException
     * @throws InvalidIsbnException
     * @throws InvalidUserException 
     */
    void importFile(String filename) throws IOException, UnrecognizedEntryException, ImportFileException, MaxCreatorsException, UnknownCategoryException, InvalidIsbnException, InvalidUserException {
      try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
        String line;
        while ((line = reader.readLine()) != null) {
          String[] fields = line.split(":");
          registerEntry(fields);
        }
      }
      setChanged(false);
    }
    
    /**
     * Register an entry in the library, which can be either a work or a user, from a set of fields of an import file.
     *
     * @param fields array of strings representing the fields of the entry
     * @throws UnrecognizedEntryException if the entry type is not recognized
     * @throws MaxCreatorsException if the number of creators exceeds the maximum allowed
     * @throws UnknownCategoryException if the category is unknown
     * @throws InvalidIsbnException if the ISBN is invalid
     * @throws InvalidUserException if the user data is invalid
     */
    public void registerEntry(String[] fields) throws UnrecognizedEntryException, MaxCreatorsException, UnknownCategoryException, InvalidIsbnException, InvalidUserException {
      switch (fields[0]) {
        case "DVD", "BOOK" -> registerWork(fields);
        case "USER" -> registerUser(fields);
        default -> throw new UnrecognizedEntryException(fields[0]);
      }
    }

    /**
     * Add one or more creators to the library, associating them with the given work.
     *
     * @param creators array of creators to add
     * @param work the work associated with the creators
     */
    public void addCreators(Creator[] creators, Work work) {
      String name;
      for (Creator c : creators) {
        name = c.getName();
        _creators.putIfAbsent(name, c);
        _creators.get(name).addWork(work);
      }
    }

    /**
     * Register a work in the library from a set of fields of an import file.
     *
     * @param fields array of strings representing the fields of the work
     * @throws UnrecognizedEntryException if the work type is not recognized
     * @throws MaxCreatorsException if the number of creators exceeds the maximum allowed
     * @throws UnknownCategoryException if the category is unknown
     * @throws InvalidIsbnException if the ISBN is invalid
     */
    public void registerWork(String... fields) throws UnrecognizedEntryException, MaxCreatorsException, UnknownCategoryException, InvalidIsbnException {
      Work work;
      switch (fields[0]) {
        case "DVD" -> work = registerDvd(fields);
        case "BOOK" -> work = registerBook(fields);
        default -> throw new UnrecognizedEntryException(fields[0]);
      }
      
      _works.put(_nextWorkId++, work);
      setChanged(true);
    }

    /**
     * Create a new category based on a given name of a subdivision.
     *
     * @param name the name of the category
     * @return the created category
     * @throws UnknownCategoryException if the category name is unknown
     */
    public Category newCategory(String name) throws UnknownCategoryException {
      return switch (name) {
        case "FICTION" -> new Fiction();
        case "SCITECH" -> new SciTech();
        case "REFERENCE" -> new Reference();
        default -> throw new UnknownCategoryException(name);
      };
    }

    /**
     * Register a DVD in the library from a set of fields of an import file.
     *
     * @param fields array of strings representing the fields of the DVD
     * @return the created DVD
     * @throws MaxCreatorsException if the number of creators exceeds the maximum allowed
     * @throws UnknownCategoryException if the category is unknown
     */
    public Dvd registerDvd(String... fields) throws MaxCreatorsException, UnknownCategoryException {
      String title = fields[1];

      if (fields[2].split(",").length > 1) throw new MaxCreatorsException(fields[1]);
      Creator creator = new Creator(fields[2].trim()); // only 1 Creator for Dvd's
      
      int price = Integer.parseInt(fields[3]);
      Category category = newCategory(fields[4]);
      String igac = fields[5];
      int copies = Integer.parseInt(fields[6]);

      Dvd dvd = new Dvd(_nextWorkId, title, creator, price, category, copies, igac);
      addCreators(new Creator[] { creator }, dvd);
      return dvd;
    }

    /**
     * Register a book in the library from a set of fields of an import file.
     *
     * @param fields array of strings representing the fields of the book
     * @return the created book
     * @throws MaxCreatorsException if the number of creators exceeds the maximum allowed
     * @throws UnknownCategoryException if the category is unknown
     * @throws InvalidIsbnException if the ISBN is invalid
     */
    public Book registerBook(String... fields) throws MaxCreatorsException, UnknownCategoryException, InvalidIsbnException {
      String title = fields[1];

      // Split creators and add each one
      String[] names = fields[2].split(",");
      Creator[] creators = new Creator[names.length];
      for (int i = 0; i < names.length; i++) { creators[i] = new Creator(names[i].trim()); }

      int price = Integer.parseInt(fields[3]);
      Category category = newCategory(fields[4]);

      String isbn = fields[5];
      if (isbn.length() != 10 && isbn.length() != 13) throw new InvalidIsbnException(isbn);

      int copies = Integer.parseInt(fields[6]);
    
      Book book = new Book(_nextWorkId, title, creators, price, category, copies, isbn);
      addCreators(creators, book);
      return book;
    }

    /**
     * Register a user in the library from a set of fields of an import file.
     *
     * @param fields array of strings representing the fields of the user
     * @throws InvalidUserException if the user data is invalid
     */
    public void registerUser(String... fields) throws InvalidUserException { registerUser(fields[1], fields[2]); }

    /**
     * Register a user in the library with the given name and email.
     * 
     * @param name the name of the user
     * @param email the email of the user
     * @return the ID of the registered user
     * @throws InvalidUserException if the username or email are blank/empty
     */
    public int registerUser(String name, String email) throws InvalidUserException{
      if (name.isBlank() || email.isBlank()) throw new InvalidUserException(name, email);
      _users.put(_nextUserId, new User(_nextUserId, name, email));
      setChanged(true);
      return _nextUserId++;
    }

    /**
     * Update all users in the library, typically called when advancing the date.
     */
    public void updateUsers() { for (User user : _users.values()) updateUserState(user); }

    public int getDate() { return _date; }

    /**
     * Advance the library's date by a specified number of days and update users accordingly.
     *
     * @param days number of days to advance
     */
    public void advanceDate(int days) {
      _date += days;
      updateUsers();
      setChanged(true);
    }

    /**
     * Retrieve a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return the user with the specified ID
     * @throws UserNotFoundException if no user with the given ID exists
     */
    public User getUser(int id) throws UserNotFoundException {
      User user = _users.get(id);
      if (user == null) throw new UserNotFoundException(id);
      return user;
    }

    /**
     * Retrieve a work by its ID.
     *
     * @param id the ID of the work to retrieve
     * @return the work with the specified ID
     * @throws WorkNotFoundException if no work with the given ID exists
     */
    public Work getWork(int id) throws WorkNotFoundException {
      Work work = _works.get(id);
      if (work == null) throw new WorkNotFoundException(id);
      return work;
    }

    /**
     * Retrieve a creator by their name.
     *
     * @param name the id(name) of the creator to retrieve
     * @return the creator with the specified name
     * @throws CreatorNotFoundException if no creator with the given name exists
     */
    public Creator getCreator(String name) throws CreatorNotFoundException {
      Creator creator = _creators.get(name);
      if (creator == null || creator.isDeleted()) throw new CreatorNotFoundException(name);
      return creator;
    }

    /**
     * Select users based on a given selector.
     * 
     * @param selector the selector to filter users
     * @return the list of users matching the selector
     */
    public List<User> selectUsers(Selector<User> selector) {
      return _users.values().stream()
                            .sorted()
                            .filter(selector::ok)
                            .collect(Collectors.toList());
    }

    /**
     * Select works based on a given selector.
     * 
     * @param selector the selector to filter works
     * @return the list of works (not deleted) matching the selector
     */
    public List<Work> selectWorks(Selector<Work> selector) {
      return _works.values().stream()
                            .sorted()
                            .filter(work -> work.isAvailable() && selector.ok(work))
                            .collect(Collectors.toList());
    }

    /**
     * Select works based on a given creator's id.
     * 
     * @param name the id (name) of the creator
     * @return the list of works (not deleted) made by that creator
     * @throws CreatorNotFoundException if the creator with the given id does not exist
     */
    public List<Work> selectWorksByCreator(String name) throws CreatorNotFoundException {
      return getCreator(name).getWorks().stream()
                                        .sorted(WorkComparator.byTitle())
                                        .filter(work -> work.isAvailable())
                                        .collect(Collectors.toList());
    }

    /**
     * Update the inventory of a work by a specified amount.
     *
     * @param id the ID of the work to update
     * @param amount the amount to update the inventory by (positive to add, negative to remove)
     * @throws WorkNotFoundException if the work with the given ID does not exist
     * @throws InvalidUpdateAmountException if the update amount is invalid (resulting in negative remaining or exceeding total copies)
     */
    public void updateWorkInventory(int id, int amount) throws WorkNotFoundException, InvalidUpdateAmountException {
      Work work = getWork(id);
      int total = work.getTotalCopies();
      int result = total + amount;

      if (result < 0) throw new InvalidUpdateAmountException();
      else if (result == 0) removeWork(work, id);
      // add more available; re-registers all creators when the work is available again
      else if (total == 0 && work instanceof WorkCreators wc)
        for (Creator c : wc.getCreators()) c.setDeleted(false);
      work.updateTotalCopies(amount);
      setChanged(true);
    }

    /**
     * Remove a work from the library, by removing any of its creators that no longer has associated works.
     * <p>
     * Note that the work itself is not removed (only it's available copies are 0), so that it's possible to add back inventory.
     *
     * @param work the work to remove
     * @param id the ID of the work to remove
     */
    public void removeWork(Work work, int id) {
      if (work instanceof WorkCreators wc) for (Creator c : wc.getCreators()) {
        c.removeWork(work);
        if (!c.hasWorks()) c.setDeleted(true);;
      }
      setChanged(true);
    }

    /**
     * Search for works in the library based on a search string.
     *
     * @param search the string to filter works
     * @return the list of works matching the search criteria
     */
    public List<Work> searchWorks(String search) {
      return _works.values().stream()
                            .filter(work -> work.matches(search.toLowerCase()))
                            .sorted()
                            .collect(Collectors.toList());
    }

    /**
     * Create a request for a user to borrow a work, if it complies with the library's rules.
     *
     * @param userId the ID of the user making the request
     * @param workId the ID of the work being requested
     * @return the due date of the request
     * @throws InvalidRequestException  if the request does not comply with the rules
     * @throws UserNotFoundException if the user with the given ID does not exist
     * @throws WorkNotFoundException if the work with the given ID does not exist
     */
    public int requestWork(int userId, int workId) throws InvalidRequestException, UserNotFoundException, WorkNotFoundException {
      int dueDate;
      User user = getUser(userId);
      Work work = getWork(workId);
      int failedId = _rules.failed(user, work);

      if (failedId == RequestRule.RULE_SUCCESS) {
        // dueDate = current date + loanPeriod based on user behavior and work availability
        dueDate = _date + user.getBehavior().getLoanPeriod(work);
        user.getRequests().add(new Request(work, dueDate));
        work.updateAvailableCopies(-1, true);
        setChanged(true);
        return dueDate;
      } else throw new InvalidRequestException(failedId);
    }

    /**
     * Return of a work by a user if requested, calculating any applicable fines.
     *
     * @param userId the ID of the user returning the work
     * @param workId the ID of the work being returned
     * @return the fine amount if the work is returned late, otherwise 0
     * @throws UserNotFoundException if the user with the given ID does not exist
     * @throws WorkNotFoundException if the work with the given ID does not exist
     * @throws NotBorrowedWorkException if the work was not borrowed by the user
     */
    public int returnWork(int userId, int workId) throws UserNotFoundException, WorkNotFoundException, NotBorrowedWorkException {
      User user = getUser(userId);
      Work work = getWork(workId);
      List<Request> requests = user.getRequests();
      int fine = 0;

      for (Request request : requests) if (request.getWork().getId() == workId) {
        int dueDate = request.getDueDate();
        if (dueDate < _date) { // return after due date; failure
          fine = user.addFine(_date - dueDate);
          user.registerFailure();
        }
        else user.registerSuccess(); // return before due date; success
        requests.remove(request);
        work.updateAvailableCopies(1, false);
        updateUserState(user);
        if (work.getObservers().stream().anyMatch(o -> o.equals(user))) work.removeObserver(user);
        setChanged(true);
        return fine;
      }
      throw new NotBorrowedWorkException(workId, userId);
    }

    /**
     * Pay a fine of a user by a specified amount.
     *
     * @param userId the ID of the user paying the fine
     * @param amount the amount to pay towards the fine
     * @throws UserNotFoundException if the user with the given ID does not exist
     */
    public void payUserFine(int userId, int amount) throws UserNotFoundException {
      getUser(userId).payFine(amount);
      setChanged(true);
    }

    /**
     * Update the state of a user, typically after paying a fine.
     *
     * @param userId the ID of the user to update
     * @throws UserNotFoundException if the user with the given ID does not exist
     */
    public void updateUserState(int userId) throws UserNotFoundException { updateUserState(getUser(userId)); }

    /**
     * Update the state of a user based on their requests and the current date.
     *
     * @param user the user to update
     */
    public void updateUserState(User user) {
      user.updateState(_date);
      setChanged(true);
    }

    /**
     * Clear the fine of a suspended user.
     *
     * @param userId the ID of the user
     * @throws UserNotFoundException if the user with the given ID does not exist
     * @throws UserNotSuspendedException if the user is not suspended
     */
    public void clearUserFine(int userId) throws UserNotFoundException, UserNotSuspendedException {
      User user = getUser(userId);
      if (!user.isSuspended()) throw new UserNotSuspendedException(userId);
      user.clearFine();
      setChanged(true);
      updateUserState(user);
    }
    
    /**
     * Retrieve a user's notifications by their ID.
     *
     * @param userId the ID of the user
     * @return the user with the specified ID
     * @throws UserNotFoundException if no user with the given ID exists
     */
    public List<Notification> getUserNotifications(int userId) throws UserNotFoundException {
      return getUser(userId).getNotifications();
    }

    /**
     * Clear a user's notifications by their ID.
     *
     * @param userId the ID of the user
     * @throws UserNotFoundException if no user with the given ID exists
     */
    public void clearUserNotifications(int userId) throws UserNotFoundException { getUser(userId).clearNotifications(); }

    /**
     * Retrieve a user's notifications by their ID.
     *
     * @param userId the ID of the user
     * @param workId the ID of the work
     * @return the user with the specified ID
     * @throws UserNotFoundException if no user with the given ID exists
     * @throws WorkNotFoundException if no work with the given ID exists
     */
    public void setWorkReturnNotification(int userId, int workId) throws UserNotFoundException, WorkNotFoundException {
      User user = getUser(userId);
      user.setObserverType(ObserverType.AVAILABILITY);
      getWork(workId).registerObserver(user);
    }

}
