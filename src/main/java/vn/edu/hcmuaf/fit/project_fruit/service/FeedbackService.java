package vn.edu.hcmuaf.fit.project_fruit.service;
import vn.edu.hcmuaf.fit.project_fruit.dao.FeedbackDao;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.Feedback;

import java.util.List;

public class FeedbackService {
    private final FeedbackDao feedbackDao = new FeedbackDao();

    // Sửa phương thức để gọi đúng tên phương thức trong FeedbackDao
    public List<Feedback> getAllFeedback() {
        return feedbackDao.getAllFeedback();  // Gọi phương thức đúng tên
    }
}
