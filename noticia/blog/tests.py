from django.test import TestCase
from django.utils import timezone
from django.core.urlresolvers import reverse
from django.test.client import Client
from blog.models import NewsItem
from .views import NewsItemForm


class NewsItemTest(TestCase):
    def test_index(self):
        resp = self.client.get(reverse('blog:index'))
        self.assertEqual(resp.status_code, 200)

    def test_noticia(self):
        resp = self.client.get(reverse('blog:noticias'))
        self.assertEqual(resp.status_code, 200)

    def test_evento(self):
        resp = self.client.get(reverse('blog:eventos'))
        self.assertEqual(resp.status_code, 200)

    def test_both(self):
        resp = self.client.get(reverse('blog:todo'))
        self.assertEqual(resp.status_code, 200)

    def test_class_form(self):
        w = NewsItem.objects.create(title='Foo', description='Bar', publish_date='2016-03-29 14:00:00')
        data = {'title': w.title, 'description': w.description, 'publish_date': w.publish_date, }
        form = NewsItemForm(data=data)
        self.assertTrue(form.is_valid())

#    def test_v1detail(self):
#            resp = self.client.get(reverse('blog:1detailnews', kwargs={'blog_id': w.id))
#            self.assertEqual(resp.status_code, 200)
