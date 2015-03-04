from django.core.urlresolvers import reverse
from django.db import models


class News(models.Model):
    title = models.CharField(max_length=100)
    description = models.TextField()
    new_date = models.DateTimeField(auto_now_add=True)

    def get_absolute_url(self):
        return reverse('news_update', args=(self.id,))

    def __unicode__(self):
        return self.title
