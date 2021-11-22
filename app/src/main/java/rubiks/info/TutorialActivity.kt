package rubiks.info

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ScrollView

class TutorialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        val elementsToSetOnClickListener = ArrayList<TutorialSection>()

        // scrollView as ViewGroup - to perform transition
        val scrollView = findViewById<ViewGroup>(R.id.tutorial_scroll_view)

        // 0. introduction
        val introductionSectionObject = TutorialSection(
            findViewById(R.id.introduction_section),
            findViewById(R.id.introduction_section_button),
            findViewById(R.id.introduction_section_expansion)
        )
        elementsToSetOnClickListener.add(introductionSectionObject)

        // 1. white cross
        val whiteCrossSectionObject = TutorialSection(
            findViewById(R.id.white_cross_section),
            findViewById(R.id.white_cross_section_button),
            findViewById(R.id.white_cross_section_expansion)
        )
        elementsToSetOnClickListener.add(whiteCrossSectionObject)

        // 2. white face corners
        val whiteFaceCornersObject = TutorialSection(
            findViewById(R.id.white_face_corners_section),
            findViewById(R.id.white_face_corners_section_button),
            findViewById(R.id.white_face_corners_section_expansion)
        )
        elementsToSetOnClickListener.add(whiteFaceCornersObject)

        // 3. second layer edges
        val secondLayerEdgesObject = TutorialSection(
            findViewById(R.id.second_layer_edges_section),
            findViewById(R.id.second_layer_edges_section_button),
            findViewById(R.id.second_layer_edges_section_expansion),
        )
        elementsToSetOnClickListener.add(secondLayerEdgesObject)

        // 4. yellow cross
        val yellowCrossObject = TutorialSection(
            findViewById(R.id.yellow_cross_section),
            findViewById(R.id.yellow_cross_section_button),
            findViewById(R.id.yellow_cross_section_expansion),
        )
        elementsToSetOnClickListener.add(yellowCrossObject)

        // 5. yellow cross edges
        val yellowCrossEdgesObject = TutorialSection(
            findViewById(R.id.yellow_cross_edges_section),
            findViewById(R.id.yellow_cross_edges_section_button),
            findViewById(R.id.yellow_cross_edges_section_expansion),
        )
        elementsToSetOnClickListener.add(yellowCrossEdgesObject)

        // 6. yellow cross corners part 1
        val yellowCrossCornersPartOneObject = TutorialSection(
            findViewById(R.id.yellow_cross_corners_section),
            findViewById(R.id.yellow_cross_corners_section_button),
            findViewById(R.id.yellow_cross_corners_section_expansion),
        )
        elementsToSetOnClickListener.add(yellowCrossCornersPartOneObject)

        // 7. yellow cross corners rotation
        val yellowCrossCornersPartTwoObject = TutorialSection(
            findViewById(R.id.yellow_cross_corners_rotation_section),
            findViewById(R.id.yellow_cross_corners_rotation_section_button),
            findViewById(R.id.yellow_cross_corners_rotation_section_expansion),
        )
        elementsToSetOnClickListener.add(yellowCrossCornersPartTwoObject)

        setOnClickListeners(elementsToSetOnClickListener, scrollView)
    }

    private fun changeSectionVisibility(
        section: LinearLayout,
        button: ImageButton,
        scrollView: ViewGroup
    ) {
        if (section.visibility == View.GONE) {
            TransitionManager.beginDelayedTransition(scrollView, AutoTransition())
            section.visibility = View.VISIBLE
            button.setImageResource(R.drawable.ic_24_collapse_arrow)

            // setting scrollView variable from ViewGroup back to ScrollView to apply smoothScrollBy method
            val scrollView1 = scrollView as ScrollView
            Handler(Looper.getMainLooper()).postDelayed({
                scrollView1.smoothScrollBy(0, 120)
            }, 350)
        } else {
            TransitionManager.beginDelayedTransition(scrollView, AutoTransition())
            section.visibility = View.GONE
            button.setImageResource(R.drawable.ic_24_expand_arrow)
        }
    }

    private fun setOnClickListeners(
        sectionsArray: ArrayList<TutorialSection>,
        scrollView: ViewGroup
    ) {
        for (section in sectionsArray) {
            section.sectionToSetOnClickListener.setOnClickListener {
                changeSectionVisibility(
                    section.sectionToExpand,
                    section.buttonToSetOnClickListener,
                    scrollView
                )
            }

            section.buttonToSetOnClickListener.setOnClickListener {
                changeSectionVisibility(
                    section.sectionToExpand,
                    section.buttonToSetOnClickListener,
                    scrollView
                )
            }
        }
    }
}