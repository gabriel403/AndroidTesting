package co.g403.android.flickr.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.rxjava3.core.Observable
import org.hamcrest.core.IsEqual.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FlickrRepositoryTest {
	@Rule
	@JvmField
	var rule = InstantTaskExecutorRule()

	companion object {
		@ClassRule
		@JvmField
		val schedulers = RxImmediateSchedulerRule()
	}

	@Mock
	lateinit var flickerService: FlickrService
	// Test rule for making the RxJava to run synchronously in unit test
	private lateinit var flickrRepository: FlickrRepository

	@Before
	fun setup() {
		flickrRepository = FlickrRepository(flickerService)
	}

	@Test
	fun getEmptySearchResults() {
		val flickrResp = FlickrSearchModel.Response(
			FlickrSearchModel.Photos(
				1,
				1,
				5,
				0,
				listOf(),
			)
		)
		Mockito.`when`(flickerService.searchImages())
			.thenReturn(Observable.just(flickrResp))

		assertTrue(
			"Empty list", flickrRepository.getSearchResults().value!!.isEmpty()
		)
	}

	@Test
	fun getListSingleSearchResult() {
		val flickrResp = FlickrSearchModel.Response(
			FlickrSearchModel.Photos(
				1,
				1,
				5,
				1,
				listOf(FlickrSearchModel.SearchImage("1", "me", "asd", "kitty", true)),
			)
		)
		Mockito.`when`(flickerService.searchImages())
			.thenReturn(Observable.just(flickrResp))

		val expectedResponse = listOf<FlickrImageModel.SizedImage>(FlickrImageModel.SizedImage("1"))
		assertThat("List of single", flickrRepository.getSearchResults().value, equalTo(
			expectedResponse))

		assertFalse(
			"Empty list", flickrRepository.getSearchResults().value!!.isEmpty()
		)
	}
}
