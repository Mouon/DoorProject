package moon.hellomoon.service.QnA;

import moon.hellomoon.domain.Member;
import moon.hellomoon.domain.Project;
import moon.hellomoon.domain.Question;
import moon.hellomoon.dto.QnA.LikeRequest;
import moon.hellomoon.dto.QnA.QuestionRequest;
import moon.hellomoon.dto.QnA.QuestionResponse;
import moon.hellomoon.dto.project.ProjectInsertRequest;
import moon.hellomoon.repository.jpaRepository.MemberRepository;
import moon.hellomoon.repository.jpaRepository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, MemberRepository memberRepository) {
        this.questionRepository = questionRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void incrementLikeCount(LikeRequest likeRequest) {
        if (likeRequest.isLike()) {
            Optional<Question> questionOptional = questionRepository.findById(likeRequest.getQuestionId());
            questionOptional.ifPresent(question -> {
                question.setLikeCount(question.getLikeCount() + 1);
                questionRepository.save(question);
            });
        }
    }

    public List<QuestionResponse> showQuestionList(){
        List<Question> questions = questionRepository.findAll();
        return mapToResponse(questions);
    }

    public List<QuestionResponse> showUserQuestionList(long userId){
        List<Question> questions = questionRepository.findByUser(userId);
        return mapToResponse(questions);
    }

    public List<QuestionResponse> showTopQuestionList(){
        List<Question> questions = questionRepository.findTop3LikedQuestionsOfTheMonth();
        return mapToResponse(questions);
    }


    @Transactional
    public void addQuestion(QuestionRequest request){
        Question question = mapToQuestionDomain(request);
        questionRepository.save(question);
    }

    /** response화 시키는 매서드 */
    public List<QuestionResponse> mapToResponse(List<Question> questions){
        List<QuestionResponse> responses = new ArrayList<>();
        for(Question question : questions){
            QuestionResponse response = new QuestionResponse();
            response.setId(question.getId());
            response.setMemberId(question.getMember() != null ? question.getMember().getId() : null);
            response.setTitle(question.getTitle());
            response.setContent(question.getContent());
            response.setLikeCount(question.getLikeCount());
            responses.add(response);
        }
        return responses;
    }
    /** request화 시키는 코드 */
    private Question mapToQuestionDomain(QuestionRequest request){
        Question question =new Question();
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));
        question.setMember(member);
        question.setTitle(request.getTitle());
        question.setContent(request.getContent());
        return question;
    }

}
