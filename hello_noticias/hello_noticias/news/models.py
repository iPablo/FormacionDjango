from django.db import models
from django.forms import ModelForm


class News(models.Model):
    title = models.CharField(max_length=100)
    description = models.CharField(max_length=300)
    new_date = models.DateTimeField(auto_now_add=True)

    def __unicode__(self):
        return "Title: %s \nDescription: %s \nDate: %s" % (self.title,
                                                           self.description,
                                                           self.new_date)


class NewsForm(ModelForm):
    class Meta:
        model = News
        fields = ['title', 'description']
