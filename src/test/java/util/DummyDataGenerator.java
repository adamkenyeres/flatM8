package util;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import model.chat.ChatContact;
import model.chat.ChatMessage;
import model.criteria.BaseCriteria;
import model.criteria.GenderCriteria;
import model.criteria.LifestyleCriteria;
import model.criteria.RoomTypeCriteria;
import model.flat.Address;
import model.flat.Flat;
import model.flat.FlatType;
import model.flatmate.FlatMateEntry;
import model.flatmate.RoomCriteria;
import model.request.AddMateRequest;
import model.request.DeleteFlatRequest;
import model.request.DeleteMateRequest;
import model.request.RequestStatus;
import model.tenant.User;

import java.util.Collections;
import java.util.Date;

public class DummyDataGenerator {

    private static User DUMMY_USER;
    private static Flat DUMMY_FLAT;
    private static Address DUMMY_ADDRESS;
    private static ChatMessage DUMMY_CHAT_MESSAGE;
    private static ChatContact DUMMY_CHAT_CONTACT;
    private static FlatMateEntry DUMMY_FLATMATE_ENTRY;
    private static RoomCriteria DUMMY_ROOM_CRITERIA;
    private static BaseCriteria DUMMY_BASE_CRITERIA;
    private static AddMateRequest DUMMY_ADD_MATE_REQUEST;
    private static DeleteFlatRequest DUMMY_DELETE_FLAT_REQUEST;
    private static DeleteMateRequest DUMMY_DELETE_MATE_REQUEST;

    public static User generateDummyUserSingleton() {
        if (DUMMY_USER == null) {
            DUMMY_USER = generateDummyUser();
        }

        return DUMMY_USER;
    }

    public static Flat generateDummyFlatSingleton() {
        if (DUMMY_FLAT == null) {
            DUMMY_FLAT = generateDummyFlat();
        }

        return DUMMY_FLAT;
    }

    public static Address generateDummyAddressSingleton() {
        if (DUMMY_ADDRESS == null) {
            DUMMY_ADDRESS = generateDummyAddress();
        }

        return DUMMY_ADDRESS;
    }

    public static User generateDummyUser() {
        return generateDummyUser("test@test.hu");
    }

    public static User generateDummyUser(String email) {
        User user = new User();
        user.setEmail(email);
        user.setFirstName("Test");
        user.setLastName("Janos");

        return user;
    }

    public static Address generateDummyAddress() {
        Address address = new Address();
        address.setCity("Budapest");
        address.setDistrict(9);
        address.setStreetAddress("Balazs Bela utca");
        address.setStreetNumber(45);
        address.setFloor(4);
        address.setDoor(1);
        return address;
    }

    public static Flat generateDummyFlat() {
        Flat flat = new Flat();
        flat.setAddress(generateDummyAddress());
        flat.setCapacity(FLAT_CAPACITY);
        flat.setFlatType(FlatType.FLAT);
        flat.setUserEmail("test@test.hu");
        flat.setRoomCount(FLAT_ROOMCOUNT);
        flat.setFlatMates(Lists.newArrayList(generateDummyUser()));
        return flat;
    }

    public static ChatMessage generateDummyChatMessage() {
        if (DUMMY_CHAT_MESSAGE == null) {
            DUMMY_CHAT_MESSAGE = new ChatMessage();
            DUMMY_CHAT_MESSAGE.setChatContact(generateDummyChatContact());
            DUMMY_CHAT_MESSAGE.setMessage("Test");
            DUMMY_CHAT_MESSAGE.setReceivers(Sets.newHashSet(generateDummyUser()));
            DUMMY_CHAT_MESSAGE.setSender(generateDummyUser());
            DUMMY_CHAT_MESSAGE.setTimestamp(new Date());
        }
        return DUMMY_CHAT_MESSAGE;
    }

    public static ChatContact generateDummyChatContact() {
        if (DUMMY_CHAT_CONTACT == null) {
            DUMMY_CHAT_CONTACT = new ChatContact();
            DUMMY_CHAT_CONTACT.setContactEntry(generateDummyFlatMateEntry());
            DUMMY_CHAT_CONTACT.setSenderEmail("test@test.hu");
        }
        return DUMMY_CHAT_CONTACT;
    }

    public static FlatMateEntry generateDummyFlatMateEntry() {
        if (DUMMY_FLATMATE_ENTRY == null) {
            DUMMY_FLATMATE_ENTRY = new FlatMateEntry();
            DUMMY_FLATMATE_ENTRY.setFlat(generateDummyFlat());
            DUMMY_FLATMATE_ENTRY.setPhotos(Collections.emptyList());
            DUMMY_FLATMATE_ENTRY.setRoomCriteria(generateDummyRoomCriteria());
        }
        return DUMMY_FLATMATE_ENTRY;
    }

    public static RoomCriteria generateDummyRoomCriteria() {
        if (DUMMY_ROOM_CRITERIA == null) {
            DUMMY_ROOM_CRITERIA = new RoomCriteria();
            DUMMY_ROOM_CRITERIA.setAdditionalDetails(Collections.emptyList());
            DUMMY_ROOM_CRITERIA.setCapacity(ROOM_SIZE);
            DUMMY_ROOM_CRITERIA.setCriteria(generateDummyBaseCriteria());
            DUMMY_ROOM_CRITERIA.setSize(ROOM_CAPACITY);
        }
        return DUMMY_ROOM_CRITERIA;
    }

    public static BaseCriteria generateDummyBaseCriteria() {
        if (DUMMY_BASE_CRITERIA == null) {
            DUMMY_BASE_CRITERIA = new BaseCriteria();
            DUMMY_BASE_CRITERIA.setAgeCriteria(AGE_CRITERIA);
            DUMMY_BASE_CRITERIA.setAgeOffset(AGE_OFFSET);
            DUMMY_BASE_CRITERIA.setGenderCriteria(GENDER_CRITERIA);
            DUMMY_BASE_CRITERIA.setRoomTypeCriteria(ROOM_CRITERIA);
            DUMMY_BASE_CRITERIA.setLifestyleCriteria(LIFESTYLE_CRITERIA);
        }
        return DUMMY_BASE_CRITERIA;
    }

    public static AddMateRequest generateDummyAddMateRequest() {
        if (DUMMY_ADD_MATE_REQUEST == null) {
            DUMMY_ADD_MATE_REQUEST = new AddMateRequest();
            DUMMY_ADD_MATE_REQUEST.setMateToAdd(generateDummyUser());
            DUMMY_ADD_MATE_REQUEST.setRequestStatus(RequestStatus.PENDING);
            DUMMY_ADD_MATE_REQUEST.setFlat(generateDummyFlat());
            DUMMY_ADD_MATE_REQUEST.setSender(generateDummyUser());
            DUMMY_ADD_MATE_REQUEST.setReceivers(Lists.newArrayList(generateDummyUser()));
            DUMMY_ADD_MATE_REQUEST.setId("XXX");
        }
        return DUMMY_ADD_MATE_REQUEST;
    }

    public static DeleteFlatRequest generateDeleteFlatRequest() {
        if (DUMMY_DELETE_FLAT_REQUEST == null) {
            DUMMY_DELETE_FLAT_REQUEST = new DeleteFlatRequest();
            DUMMY_DELETE_FLAT_REQUEST.setId("XXX");
        }
        return DUMMY_DELETE_FLAT_REQUEST;
    }

    public static DeleteMateRequest generateDeleteMateRequest() {
        if (DUMMY_DELETE_MATE_REQUEST == null) {
            DUMMY_DELETE_MATE_REQUEST = new DeleteMateRequest();
            DUMMY_DELETE_MATE_REQUEST.setId("XXX");
        }
        return DUMMY_DELETE_MATE_REQUEST;
    }

    public static final Integer AGE_CRITERIA = 20;
    public static final Integer AGE_OFFSET = 3;
    public static final Integer AGE_IN_RADIUS = AGE_CRITERIA + AGE_OFFSET - 1;
    public static final Integer AGE_OUT_OF_RADIUS = AGE_CRITERIA + AGE_OFFSET + 1;
    public static final Integer ROOM_SIZE = 50;
    public static final Integer ROOM_CAPACITY = 1;
    public static final Integer FLAT_CAPACITY = 2;
    public static final Integer FLAT_ROOMCOUNT = 2;
    public static final LifestyleCriteria LIFESTYLE_CRITERIA = LifestyleCriteria.WORK;
    public static final GenderCriteria GENDER_CRITERIA = GenderCriteria.FEMALE;
    public static final RoomTypeCriteria ROOM_CRITERIA = RoomTypeCriteria.PRIVATE_ROOM;
}
