from django import forms

from hello_noticias.news.models import News


class NewsForm(forms.ModelForm):

    class Meta:
        model = News
        fields = ('title', 'description')
        widgets = {
            'description': forms.Textarea(attrs={'cols': 80,
                                                 'rows': 20})
        }
